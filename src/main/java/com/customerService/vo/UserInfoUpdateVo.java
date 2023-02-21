package com.customerService.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
public class UserInfoUpdateVo {

    @NotNull(message = "用户id不能为空")
    private Long id;                    // 用户id

    private String username;            // 登录账号

    @Pattern(regexp = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\\\W_!@#$%^&*`~()-+=<>,./;':\"]{8,16}$"
            , message = "密码必须包含大小写字母 、数字、 特殊字符（四种里至少三种，且至少8位,不能包含空格）")
    private String password;            // 密码

    private String company;             // 所属公司

    private String nickname;            // 昵称

    private String idNumber;            // 身份证号

    private String externalNickname;    // 对外显示昵称

    @Email(message = "邮件格式填写错误")
    private String email;               // 邮件

    private String phone;               // 电话

    private String country;             // 国家

    private String defaultLanguage;     // 默认语言
}
