package com.customerService.controller;

import com.customerService.domain.UserInfoSum;
import com.customerService.domain.request.UserInfoReq;
import com.customerService.service.UserService;
import com.customerService.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/user/info")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/save")
    public Result saveUserInfo(@RequestBody UserInfoReq userInfoReq) {

        // 接收请求参数，封装到实体类
        UserInfoSum userInfoSum = new UserInfoSum();
        userInfoSum.setUsername(userInfoReq.getUsername());
        userInfoSum.setPassword(userInfoReq.getPassword());
        userInfoSum.setCompany(userInfoReq.getCompany());
        userInfoSum.setNickname(userInfoReq.getNickname());
        userInfoSum.setCreateTime(LocalDateTime.now());
        userInfoSum.setExternalNickname(userInfoReq.getExternalNickname());
        userInfoSum.setEmail(userInfoReq.getEmail());
        userInfoSum.setPhone(userInfoReq.getPhone());
        userInfoSum.setCountry(userInfoReq.getCountry());
        userInfoSum.setDefaultLanguage(userInfoReq.getDefaultLanguage());

        // 调用业务层方法
        Boolean flag = userService.saveUserInfo(userInfoSum);

        if (flag) {
            log.info("客服信息添加成功");
            return new Result(HttpStatus.OK.value(), "客服信息添加成功");
        } else {
            log.info("客服信息添加失败");
            return new Result(HttpStatus.NOT_FOUND.value(), "客服信息添加失败");
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result removeUserInfo(@PathVariable Integer id) {
        Boolean flag = userService.removeById(id);
        if (flag) {
            log.info("客服信息删除成功");
            return new Result(HttpStatus.OK.value(), "客服信息删除成功");
        } else {
            log.info("客服信息删除失败");
            return new Result(HttpStatus.NOT_FOUND.value(), "客服信息删除失败");
        }
    }

    @PutMapping("/update")
    public Result updateUserInfo(@RequestBody UserInfoReq userInfoReq) {

        // 接收请求参数，封装到实体类
        UserInfoSum userInfoSum = new UserInfoSum();
        userInfoSum.setId(userInfoReq.getId());
        userInfoSum.setUsername(userInfoReq.getUsername());
        userInfoSum.setPassword(userInfoReq.getPassword());
        userInfoSum.setCompany(userInfoReq.getCompany());
        userInfoSum.setNickname(userInfoReq.getNickname());
        userInfoSum.setUpdateTime(LocalDateTime.now());
        userInfoSum.setExternalNickname(userInfoReq.getExternalNickname());
        userInfoSum.setEmail(userInfoReq.getEmail());
        userInfoSum.setPhone(userInfoReq.getPhone());
        userInfoSum.setCountry(userInfoReq.getCountry());
        userInfoSum.setDefaultLanguage(userInfoReq.getDefaultLanguage());

        // 调用业务层方法
        Boolean flag = userService.updateUserInfo(userInfoSum);

        if (flag) {
            log.info("客服信息修改成功");
            return new Result(HttpStatus.OK.value(), "客服信息修改成功");
        } else {
            log.info("客服信息修改失败");
            return new Result(HttpStatus.NOT_FOUND.value(), "客服信息修改失败");
        }
    }

    @GetMapping("/get/{id}")
    public Result getUserInfoById(@PathVariable Integer id) {
        UserInfoSum userInfo = userService.getUserInfoById(id);
        if (userInfo != null) {
            log.info("客服信息查询成功");
            return new Result(HttpStatus.OK.value(), "客服信息查询成功", userInfo);
        } else {
            log.info("客服信息查询失败");
            return new Result(HttpStatus.NOT_FOUND.value(), "客服信息查询失败");
        }
    }

    @GetMapping("/get")
    public Result getUserInfoAll() {
        List<UserInfoSum> userInfoSums = userService.getUserInfoAll();
        if (userInfoSums != null) {
            log.info("客服信息查询成功");
            return new Result(HttpStatus.OK.value(), "客服信息查询成功", userInfoSums);
        } else {
            log.info("客服信息查询失败");
            return new Result(HttpStatus.NOT_FOUND.value(), "客服信息查询失败");
        }
    }
}
