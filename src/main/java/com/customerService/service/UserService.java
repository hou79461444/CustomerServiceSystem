package com.customerService.service;

import com.customerService.domain.UserInfoDisplay;
import com.customerService.domain.UserInfoSum;

import java.util.List;


public interface UserService {

    // 添加客服信息表和客服信息扩展表相关信息
    boolean saveUserInfo(UserInfoSum userInfoSum);

    // 删除客服信息表（根据客服信息表id，逻辑删除）
    boolean removeById(Integer userInfoId);

    // 修改客服信息表和客服信息扩展表相关信息
    boolean updateUserInfo(UserInfoSum userInfoSum);

    // 查询客服信息表和客服信息扩展表中单个用户信息（根据客服信息表id）
    UserInfoDisplay getUserInfoById(Integer userInfoId);

    // 查询客服信息表和客服信息扩展表中所有用户信息
    List<UserInfoDisplay> getUserInfoAll();

    // 查询客服信息表中某用户是否存在（根据客服信息表的登录账号）
    boolean getUserInfoByUsername(String username);
}
