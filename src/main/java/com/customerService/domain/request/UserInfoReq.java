package com.customerService.domain.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Data
public class UserInfoReq {

    private Long id;                    // 用户id

    @NotBlank(message = "登录账号不能为空")
    private String username;            // 登录账号

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\\\W_!@#$%^&*`~()-+=<>,./;':\"]{8,16}$"
            , message = "密码必须包含大小写字母 、数字、 特殊字符（四种里至少三种，且至少8位,不能包含空格）")
    private String password;            // 密码

    @NotBlank(message = "所属公司不能为空")
    private String company;             // 所属公司

    @NotBlank(message = "昵称不能为空")
    private String nickname;            // 昵称

    @NotBlank(message = "对外显示昵称不能为空")
    private String externalNickname;    // 对外显示昵称

    @NotBlank(message = "邮件不能为空")
    @Email(message = "邮件格式填写错误")
    private String email;               // 邮件

    @NotBlank(message = "电话不能为空")
    private String phone;               // 电话

    @NotBlank(message = "国家不能为空")
    private String country;             // 国家

    @NotBlank(message = "默认语言不能为空")
    private String defaultLanguage;     // 默认语言
}
