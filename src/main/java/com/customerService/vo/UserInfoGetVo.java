package com.customerService.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class UserInfoGetVo {
    @NotNull(message = "用户id不能为空")
    private Long id;
    private String username;            // 登录账号
    private String password;            // 密码
    private String company;             // 所属公司
    private String nickname;            // 昵称
    private String idNumber;            // 身份证号
    private String createTime;          // 创建时间
    private String updateTime;          // 更新时间
    private String externalNickname;    // 对外显示昵称
    private String email;               // 邮件
    private String phone;               // 电话
    private String country;             // 国家
    private String defaultLanguage;     // 默认语言
}
