package com.customerService.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class UserInfoDeleteVo {
    @NotNull(message = "用户id不能为空")
    private Long id;
}
