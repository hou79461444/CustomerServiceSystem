package com.customerService.controller;

import com.customerService.domain.UserInfoDisplay;
import com.customerService.domain.UserInfoSum;
import com.customerService.domain.request.UserInfoReq;
import com.customerService.service.UserService;
import com.customerService.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user/info")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/save")
    public Result saveUserInfo(@Validated @RequestBody UserInfoReq userInfoReq, BindingResult bindingResult) {

        // 请求参数校验
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            log.error("请求参数校验不通过");
            // 取出所有校验不通过的信息
            List<String> collect = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return new Result(HttpStatus.BAD_REQUEST.value(), "请求参数校验不通过", collect);
        }

        // 接收请求参数，客服人员信息
        UserInfoSum userInfoSum = new UserInfoSum();
        userInfoSum.setUsername(userInfoReq.getUsername());
        // 密码进行MD5盐值加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(userInfoReq.getPassword());
        userInfoSum.setPassword(password);
        userInfoSum.setCompany(userInfoReq.getCompany());
        userInfoSum.setNickname(userInfoReq.getNickname());
        userInfoSum.setCreateTime(LocalDateTime.now());
        userInfoSum.setUpdateTime(LocalDateTime.now());
        userInfoSum.setExternalNickname(userInfoReq.getExternalNickname());
        userInfoSum.setEmail(userInfoReq.getEmail());
        userInfoSum.setPhone(userInfoReq.getPhone());
        userInfoSum.setCountry(userInfoReq.getCountry());
        userInfoSum.setDefaultLanguage(userInfoReq.getDefaultLanguage());

        // 判断数据库中该用户是否存在
        boolean isExists = userService.getUserInfoByUsername(userInfoReq.getUsername());
        if (isExists) {
            log.error("该用户已存在，客服信息添加失败");
            return new Result(HttpStatus.NOT_FOUND.value(), "该用户已存在，客服信息添加失败");
        }

        // 调用业务层方法
        boolean flag = userService.saveUserInfo(userInfoSum);

        if (flag) {
            log.info("客服信息添加成功");
            return new Result(HttpStatus.OK.value(), "客服信息添加成功");
        } else {
            log.error("客服信息添加失败");
            return new Result(HttpStatus.NOT_FOUND.value(), "客服信息添加失败");
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result removeUserInfo(@PathVariable Long id) {
        boolean flag = userService.removeById(id);
        if (flag) {
            log.info("客服信息删除成功");
            return new Result(HttpStatus.OK.value(), "客服信息删除成功");
        } else {
            log.error("客服信息删除失败");
            return new Result(HttpStatus.NOT_FOUND.value(), "客服信息删除失败");
        }
    }

    @PutMapping("/update")
    public Result updateUserInfo(@Validated @RequestBody UserInfoReq userInfoReq, BindingResult bindingResult) {

        // 请求参数校验
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            log.error("请求参数校验不通过");
            // 取出所有校验不通过的信息
            List<String> collect = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            return new Result(HttpStatus.BAD_REQUEST.value(), "请求参数校验不通过", collect);
        }

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
        boolean flag = userService.updateUserInfo(userInfoSum);

        if (flag) {
            log.info("客服信息修改成功");
            return new Result(HttpStatus.OK.value(), "客服信息修改成功");
        } else {
            log.error("客服信息修改失败");
            return new Result(HttpStatus.NOT_FOUND.value(), "客服信息修改失败");
        }
    }

    @GetMapping("/get/{id}")
    public Result getUserInfoById(@PathVariable Long id) {
        UserInfoDisplay userInfoDisplay = userService.getUserInfoById(id);
        if (userInfoDisplay != null) {
            log.info("客服信息查询成功");
            return new Result(HttpStatus.OK.value(), "客服信息查询成功", userInfoDisplay);
        } else {
            log.error("客服信息查询失败");
            return new Result(HttpStatus.NOT_FOUND.value(), "客服信息查询失败");
        }
    }

    @GetMapping("/get")
    public Result getUserInfoAll() {
        List<UserInfoDisplay> userInfoDisplays = userService.getUserInfoAll();
        if (userInfoDisplays != null) {
            log.info("客服信息查询成功");
            return new Result(HttpStatus.OK.value(), "客服信息查询成功", userInfoDisplays);
        } else {
            log.error("客服信息查询失败");
            return new Result(HttpStatus.NOT_FOUND.value(), "客服信息查询失败");
        }
    }
}
