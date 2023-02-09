package com.customerService.mapper;

import com.customerService.domain.UserInfo;
import com.customerService.domain.UserInfoExpand;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserMapper {

    // 添加客服信息表相关信息
    @Insert("insert into tbl_user_info values(null, #{username}, #{password}, #{company}, #{nickname}, #{createTime}, null, #{deleted})")
    int saveUserInfo(UserInfo userInfo);

    // 添加客服信息扩展表相关信息
    @Insert("insert into tbl_user_info_expand values(null, #{infoId}, #{externalNickname}, #{email}, #{phone}, #{country}, #{defaultLanguage})")
    int saveUserInfoExpand(UserInfoExpand userInfoExpand);

    // 删除客服信息表（根据客服信息表id，逻辑删除）
    @Delete("update tbl_user_info set deleted=1 where id=#{userInfoId}")
    int removeById(Integer userInfoId);

    // 修改客服信息表相关信息
    int updateUserInfo(UserInfo userInfo);

    // 修改客服信息扩展表相关信息
    int updateUserInfoExpand(UserInfoExpand userInfoExpand);

    // 查询客服信息表中单个用户信息（根据客服信息表id）
    @Select("select * from tbl_user_info where id=#{userInfoId}")
    @Results({
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time")
    })
    UserInfo getUserInfoById(Integer userInfoId);

    // 查询客服信息表中所有用户信息
    @Select("select * from tbl_user_info where deleted=0")
    @Results({
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time")
    })
    List<UserInfo> getUserInfoAll();

    // 查询客服信息扩展表中单个用户信息（根据客服信息表id）
    @Select("select * from tbl_user_info_expand where info_id=#{userInfoId}")
    @Results({
            @Result(property = "infoId", column = "info_id"),
            @Result(property = "externalNickname", column = "external_nickname"),
            @Result(property = "defaultLanguage", column = "default_language")
    })
    UserInfoExpand getUserInfoExpandById(Integer userInfoId);

    // 查询客服信息表主键id（根据登录账号username查询）
    @Select("select id from tbl_user_info where username=#{username}")
    int getUserInfoIdByUsername(String username);

    // 判断客服信息表是否已经逻辑删除（根据客服信息表id，判断deleted是否为0）
    @Select("select deleted from tbl_user_info where id=#{userInfoId}")
    int getIfExists(Integer userInfoId);
}
