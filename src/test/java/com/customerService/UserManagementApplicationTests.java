package com.customerService;

import com.customerService.domain.UserInfoDisplay;
import com.customerService.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserManagementApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void testRemove() {
        Long userInfoId = 2L;
        Boolean flag = userService.removeById(userInfoId);
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
