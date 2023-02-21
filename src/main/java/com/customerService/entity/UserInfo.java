package com.customerService.entity;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UserInfo {
    private Long id;                    // 主键id
    private String username;            // 登录账号
    private String password;            // 密码
    private String company;             // 所属公司
    private String nickname;            // 昵称
    private String idNumber;            // 身份证号
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 更新时间
    private Integer deleted;            // 逻辑删除标记
}
