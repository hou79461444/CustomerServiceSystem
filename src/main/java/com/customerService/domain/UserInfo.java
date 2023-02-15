package com.customerService.domain;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UserInfo {
    private Long id;                    // 主键
    private String username;            // 登录账号
    private String password;            // 密码
    private String company;             // 所属公司
    private String nickname;            // 昵称
    private LocalDateTime createTime;   // 创建时间
    private LocalDateTime updateTime;   // 更新时间
    private Integer deleted;            // 逻辑删除标记
}
