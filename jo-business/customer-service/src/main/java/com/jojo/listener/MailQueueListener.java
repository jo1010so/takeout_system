package com.jojo.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
@RabbitListener(queues = "mail")
public class MailQueueListener {
    @Resource
    JavaMailSender sender;
    @Value("${spring.mail.username}")
    String username;

    @RabbitHandler
    public void sendMailMessage(Map<String,Object> data){
        String email = data.get("email").toString();
        Integer code = (Integer) data.get("code");
        String type = (String) data.get("type");
        SimpleMailMessage message = null;
        if(type.equals("register")){
            message = createSimpleMailMessage("欢迎注册",
                    "您的邮件注册验证码为:"+code+",有效时间3分钟,为了保障您的安全，谓勿向他人泄露验证码信息。", email);
        }else if(type.equals("reset")){
            message = createSimpleMailMessage("密码重置邮件",
                    "你好,您正在进行重置密码操作,验证码:"+code+",有效时间3分钟,如非本人操作,请无视。", email);
        }
        if (message==null) {
            System.out.println("message是空的");
            return;
        }
        sender.send(message);
    }

    private SimpleMailMessage createSimpleMailMessage(String title,String content,String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(title);
        message.setText(content);
        message.setFrom(username);
        message.setTo(email);
        return message;
    }

}
