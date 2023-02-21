package com.customerService.mapper;

import com.customerService.entity.UserInfo;
import com.customerService.entity.UserInfoExpand;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserMapper {
    // 添加客服信息表相关信息
    int saveUserInfo(UserInfo userInfo);
    // 添加客服信息扩展表相关信息
    int saveUserInfoExpand(UserInfoExpand userInfoExpand);
    // 删除客服信息表（根据客服信息表id，逻辑删除）
    int removeById(Long userInfoId);
    // 修改客服信息表相关信息
    int updateUserInfo(UserInfo userInfo);
    // 修改客服信息扩展表相关信息
    int updateUserInfoExpand(UserInfoExpand userInfoExpand);
    // 查询客服信息表中单个用户信息（根据客服信息表id）
    UserInfo getUserInfoById(Long userInfoId);
    // 查询客服信息表中所有用户信息
    List<UserInfo> getUserInfoAll();
    // 查询客服信息扩展表中单个用户信息（根据客服信息表id）
    UserInfoExpand getUserInfoExpandById(Long userInfoId);
    // 查询客服信息扩展表中所有用户信息
    List<UserInfoExpand> getUserInfoExpandAll();
    // 查询客服信息表主键id（根据身份证号idNumber查询）
    long getUserInfoIdByIdNumber(String idNumber);
    // 查询客服信息表是否已经逻辑删除（根据客服信息表id，判断deleted是否为0）
    int getIfExists(Long userInfoId);
    // 查询客服信息表中单个用户信息（根据身份证号idNumber查询）
    UserInfo getUserInfoByIdNumber(String idNumber);
}
