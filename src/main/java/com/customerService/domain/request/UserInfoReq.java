package com.customerService.domain.request;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UserInfoReq {
    private Integer id;                 // 用户id
    private String username;            // 登录账号
    private String password;            // 密码
    private String company;             // 所属公司
    private String nickname;            // 昵称
    private String externalNickname;    // 对外显示昵称
    private String email;               // 邮件
    private String phone;               // 电话
    private String country;             // 国家
    private String defaultLanguage;     // 默认语言
}
