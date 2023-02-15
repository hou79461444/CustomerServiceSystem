package com.customerService;

import com.customerService.domain.UserInfoDisplay;
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
        Long userInfoId = 2L;
        Boolean flag = userService.removeById(userInfoId);
        System.out.println(flag);
    }

    @Test
    void testUpdate() {
        UserInfoSum userInfoSum = new UserInfoSum();
        userInfoSum.setId(3L);
        userInfoSum.setNickname("猛男");
        userInfoSum.setUpdateTime(LocalDateTime.now());
        userInfoSum.setCountry("法国");
        userInfoSum.setDefaultLanguage("法语");

        Boolean flag = userService.updateUserInfo(userInfoSum);
        System.out.println(flag);
    }

    @Test
    void testGetById() {
        Long userInfoId = 2L;
        UserInfoDisplay userInfoById = userService.getUserInfoById(userInfoId);
        System.out.println(userInfoById);
    }

    @Test
    void testGetAll() {
        List<UserInfoDisplay> userInfoAll = userService.getUserInfoAll();
        System.out.println(userInfoAll);
    }

    @Test
    void test() {
        boolean flag = userService.getUserInfoByUsername("79461444li");
        System.out.println(flag);
    }
}
