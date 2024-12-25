package com.jojo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRegisterVo {
    private String email;

    private String code;

    private String username;

    private String password;
}
