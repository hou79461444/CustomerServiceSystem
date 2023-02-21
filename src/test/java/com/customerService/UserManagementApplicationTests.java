package com.customerService;

import com.customerService.entity.UserInfo;
import com.customerService.entity.UserInfoExpand;
import com.customerService.mapper.UserMapper;
import com.customerService.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootTest
class UserManagementApplicationTests {

    @Autowired
    private UserMapper userMapper;

    /**
     * 数据层接口单元测试
     */

    // 添加客服信息表相关信息
    @Test
    public void testSaveUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("123456789");
        // 密码进行MD5盐值加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userInfo.setPassword(passwordEncoder.encode("123456789Aa"));
        userInfo.setCompany("公司A");
        userInfo.setNickname("小明");
        userInfo.setIdNumber("131128200509063651");
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        userInfo.setDeleted(0);
        int saveCount = userMapper.saveUserInfo(userInfo);
        System.out.println(saveCount);
    }

    // 添加客服信息扩展表相关信息
    @Test
    public void testSaveUserInfoExpand() {
        UserInfoExpand userInfoExpand = new UserInfoExpand();
        // 查询刚添加的客服信息表id
        userInfoExpand.setInfoId(1L);
        userInfoExpand.setExternalNickname("钢铁侠");
        userInfoExpand.setEmail("2046588961@qq.com");
        userInfoExpand.setPhone("13654548569");
        userInfoExpand.setCountry("中国");
        userInfoExpand.setDefaultLanguage("汉语");
        int saveCount = userMapper.saveUserInfoExpand(userInfoExpand);
        System.out.println(saveCount);
    }

    // 删除客服信息表（根据客服信息表id，逻辑删除）
    @Test
    public void testRemoveById() {
        int removeCount = userMapper.removeById(1L);
        System.out.println(removeCount);
    }

    // 修改客服信息表相关信息
    @Test
    public void testUpdateUserInfo() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1L);
        userInfo.setUsername("987654321");
        // 密码进行MD5盐值加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userInfo.setPassword(passwordEncoder.encode("987654321Aa"));
        userInfo.setCompany("公司B");
        userInfo.setNickname("小红");
        userInfo.setIdNumber("131128200906042563");
        userInfo.setUpdateTime(LocalDateTime.now());
        int updateCount = userMapper.updateUserInfo(userInfo);
        System.out.println(updateCount);
    }

    // 修改客服信息扩展表相关信息
    @Test
    public void testUpdateUserInfoExpand() {
        UserInfoExpand userInfoExpand = new UserInfoExpand();
        userInfoExpand.setInfoId(1L);
        userInfoExpand.setExternalNickname("神奇女侠");
        userInfoExpand.setEmail("2048956415@qq.com");
        userInfoExpand.setPhone("13826455986");
        userInfoExpand.setCountry("美国");
        userInfoExpand.setDefaultLanguage("英语");
        int updateCount = userMapper.updateUserInfoExpand(userInfoExpand);
        System.out.println(updateCount);
    }

    // 查询客服信息表中单个用户信息（根据客服信息表id）
    @Test
    public void testGetUserInfoById() {
        UserInfo userInfo = userMapper.getUserInfoById(1L);
        System.out.println(userInfo);
    }

    // 查询客服信息表中所有用户信息
    @Test
    public void testGetUserInfoAll() {
        List<UserInfo> userInfos = userMapper.getUserInfoAll();
        System.out.println(userInfos);
    }

    // 查询客服信息扩展表中单个用户信息（根据客服信息表id）
    @Test
    public void testGetUserInfoExpandById() {
        UserInfoExpand userInfoExpand = userMapper.getUserInfoExpandById(1L);
        System.out.println(userInfoExpand);
    }

    // 查询客服信息扩展表中所有用户信息
    @Test
    public void testGetUserInfoExpandAll() {
        List<UserInfoExpand> userInfoExpands = userMapper.getUserInfoExpandAll();
        System.out.println(userInfoExpands);
    }

    // 查询客服信息表主键id（根据身份证号idNumber查询）
    @Test
    public void testGetUserInfoIdByIdNumber() {
        long userInfoId = userMapper.getUserInfoIdByIdNumber("131128200906042563");
        System.out.println(userInfoId);
    }

    // 查询客服信息表是否已经逻辑删除（根据客服信息表id，判断deleted是否为0）
    @Test
    public void testGetIfExists() {
        int deleted = userMapper.getIfExists(1L);
        System.out.println(deleted);
    }

    // 查询客服信息表中单个用户信息（根据身份证号idNumber查询）
    @Test
    public void testGetUserInfoByIdNumber() {
        UserInfo userInfo = userMapper.getUserInfoByIdNumber("131128200906042563");
        System.out.println(userInfo);
    }
}
