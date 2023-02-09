package com.customerService;

import com.customerService.domain.UserInfoSum;
import com.customerService.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserManagementApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void testSave() {
        UserInfoSum userInfoSum = new UserInfoSum();
        userInfoSum.setUsername("54215612315li");
        userInfoSum.setPassword("541531153");
        userInfoSum.setCompany("腾讯");
        userInfoSum.setNickname("小刚");
        userInfoSum.setCreateTime(LocalDateTime.now());
        userInfoSum.setExternalNickname("钢铁侠");
        userInfoSum.setEmail("4645131135@qq.com");
        userInfoSum.setPhone("4543515313");
        userInfoSum.setCountry("美国");
        userInfoSum.setDefaultLanguage("英语");

        Boolean flag = userService.saveUserInfo(userInfoSum);
        System.out.println(flag);
    }

    @Test
    void testRemove() {
        Integer userInfoId = 2;
        Boolean flag = userService.removeById(userInfoId);
        System.out.println(flag);
    }

    @Test
    void testUpdate() {
        UserInfoSum userInfoSum = new UserInfoSum();
        userInfoSum.setId(3);
        userInfoSum.setNickname("猛男");
        userInfoSum.setUpdateTime(LocalDateTime.now());
        userInfoSum.setCountry("法国");
        userInfoSum.setDefaultLanguage("法语");

        Boolean flag = userService.updateUserInfo(userInfoSum);
        System.out.println(flag);
    }

    @Test
    void testGetById() {
        Integer userInfoId = 2;
        UserInfoSum userInfoSum = userService.getUserInfoById(userInfoId);
        System.out.println(userInfoSum);
    }

    @Test
    void testGetAll() {
        List<UserInfoSum> userInfoSums = userService.getUserInfoAll();
        System.out.println(userInfoSums);
    }
}
