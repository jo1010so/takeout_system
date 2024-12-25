package com.jojo.controller;

import cn.hutool.core.util.ObjectUtil;
import com.jojo.domain.vo.EmailRegisterVo;
import com.jojo.domain.vo.EmailRestVo;
import com.jojo.domain.vo.RestConfirmVo;
import com.jojo.model.Result;
import com.jojo.service.RegisterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Resource
    private RegisterService registerService;

    @GetMapping("/ask-code")
    public Result<?> askVerifyCode(@RequestParam @Email String email,
                                   @RequestParam @Pattern(regexp = "(register|reset)") String type,
                                   HttpServletRequest request){
        String s = registerService.registerEmailVerifyCode(email, type, request.getRemoteAddr());

        return Result.handle(ObjectUtil.isNull(s));
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody EmailRegisterVo vo){
        String s = registerService.registerEmailAccount(vo);
        if (ObjectUtil.isNull(s))
            return Result.success();
        return Result.fail(414,s);
    }

    @PostMapping("/reset-confirm")
    public Result<?> resetConfirm(@RequestBody  RestConfirmVo vo){
        String s = registerService.resetConfirm(vo);
        if (ObjectUtil.isNull(s))
            return Result.success();
        return Result.fail(414,s);
    }

    @PostMapping("/reset-password")
    public Result<?> resetConfirm(@RequestBody  EmailRestVo vo){
        registerService.resetEmailAccountPassword(vo);
        return Result.success();
    }

}
