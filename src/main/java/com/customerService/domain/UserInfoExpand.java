package com.customerService.domain;

import lombok.Data;


@Data
public class UserInfoExpand {
    private Integer id;                 // 主键
    private Integer infoId;             // 客服信息主表id
    private String externalNickname;    // 对外显示昵称
    private String email;               // 邮件
    private String phone;               // 电话
    private String country;             // 国家
    private String defaultLanguage;     // 默认语言
}
