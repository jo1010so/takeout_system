package com.jojo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jojo.constant.RegisterConstants;
import com.jojo.domain.Customer;
import com.jojo.domain.vo.EmailRegisterVo;
import com.jojo.domain.vo.EmailRestVo;
import com.jojo.domain.vo.RestConfirmVo;
import com.jojo.mapper.CustomerMapper;
import com.jojo.service.CustomerService;
import com.jojo.service.RegisterService;
import com.jojo.util.FlowUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service("registerService")
public class RegisterServiceImpl implements RegisterService {

    @Resource
    private FlowUtils flowUtils;

    @Resource
    private AmqpTemplate amqpTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private CustomerService customerService;

    @Override
    public String registerEmailVerifyCode(String email, String type,String ip) {
        synchronized (ip.intern()) {
            if (!this.verifyLimit(ip)) {
                return "请求频繁，请稍后再试";
            }
            //生成随机验证码
            Random random = new Random();
            int code = random.nextInt(899999) + 100000;
            Map<String, Object> data = new HashMap<>();
            data.put("type", type);
            data.put("email", email);
            data.put("code", code);
            //发送到邮件队列中
            amqpTemplate.convertAndSend("mail", data);
            //还要在redis中存一下验证码,3分钟时间内有效
            stringRedisTemplate.opsForValue().set(RegisterConstants.VERIFY_EMAIL_DATA + email, String.valueOf(code), 10, TimeUnit.MINUTES);
        }
        return null;
    }

    @Override
    public String registerEmailAccount(EmailRegisterVo vo) {
        String email = vo.getEmail();
        String username = vo.getUsername();
        //取redis中的验证码
        String key = RegisterConstants.VERIFY_EMAIL_DATA+email;
        String code = stringRedisTemplate.opsForValue().get(key);
        if(code == null) return "请先获取验证码";
        if(!code.equals(vo.getCode())) return "验证码输入错误，请重新输入";
        if(this.existsAccountByEmail(email)) return "此邮箱已被注册";
        if(this.existsAccountByUsername(username)) return "此用户名已被注册";
        String password = passwordEncoder.encode(vo.getPassword());

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setUsername(username);
        customer.setPassword(password);
        customer.setCreateTime(new Date());
        customer.setAuthorize(0);

        if (customerService.save(customer)) {
            //验证码使用完就可以删除了
            stringRedisTemplate.delete(key);
            return null;
        }else {
            return "内部错误，请联系管理员";
        }
    }

    @Override
    public String resetConfirm(RestConfirmVo vo) {
        String email = vo.getEmail();
        String key = RegisterConstants.VERIFY_EMAIL_DATA+email;
        String code = stringRedisTemplate.opsForValue().get(key);
        if(code == null) return "请先获取验证码";
        if(!code.equals(vo.getCode())) return "验证码输入错误，请重新输入";
        return null;
    }

    @Override
    public String resetEmailAccountPassword(EmailRestVo vo) {
        String email = vo.getEmail();
        String verify = this.resetConfirm(new RestConfirmVo(email,vo.getCode()));
        if (verify!=null) return verify;
        String password = passwordEncoder.encode(vo.getPassword());
        boolean update = customerService.update().eq("email",email).set("password",password).update();
        if(update){
            stringRedisTemplate.delete(RegisterConstants.VERIFY_EMAIL_DATA+email);
        }
        return null;
    }


    private boolean existsAccountByEmail(String email){
        return customerMapper.exists(Wrappers.<Customer>query().eq("email",email));
    }
    private boolean existsAccountByUsername(String username){
        return customerMapper.exists(Wrappers.<Customer>query().eq("username",username));
    }

    private boolean verifyLimit(String ip){
        String key = RegisterConstants.VERIFY_EMAIL_LIMIT+ip;
        return flowUtils.limitOnceCheck(key);
    }

}
