package com.jojo.service;


import com.jojo.domain.vo.EmailRegisterVo;
import com.jojo.domain.vo.EmailRestVo;
import com.jojo.domain.vo.RestConfirmVo;

public interface RegisterService {

    String registerEmailVerifyCode(String email, String type, String ip);

    public String registerEmailAccount(EmailRegisterVo vo);

    public String resetConfirm(RestConfirmVo vo);

    public String resetEmailAccountPassword(EmailRestVo vo);
}
