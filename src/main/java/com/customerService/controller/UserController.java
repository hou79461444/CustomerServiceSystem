package com.customerService.controller;

import com.customerService.vo.UserInfoDeleteVo;
import com.customerService.vo.UserInfoGetVo;
import com.customerService.vo.UserInfoSaveVo;
import com.customerService.vo.UserInfoUpdateVo;
import com.customerService.exception.BusinessException;
import com.customerService.exception.SystemException;
import com.customerService.service.UserService;
import com.customerService.util.Code;
import com.customerService.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/user/info")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    // 添加客服人员信息
    @PostMapping("/save")
    public Result saveUserInfo(@Validated @RequestBody UserInfoSaveVo userInfoSaveVo, BindingResult bindingResult) {
        // 调用业务层方法
        try {
            boolean flag = userService.saveUserInfo(userInfoSaveVo, bindingResult);
            if (flag) {
                log.info("userinfo save => saveUserInfo success");
                return new Result(HttpStatus.OK.value(), "客服信息添加成功");
            } else {
                log.error("userinfo save => saveUserInfo fail");
                return new Result(HttpStatus.NOT_FOUND.value(), "客服信息添加失败");
            }
        } catch (BusinessException e) {
            throw new BusinessException(e.getCode(), e.getMessage(), e.getData());
        } catch (Exception e) {
            throw new SystemException(Code.SYSTEM_ERR, e.getMessage(), e);
        }
    }

    // 删除客服人员信息
    @PostMapping("/delete")
    public Result removeUserInfo(@Validated @RequestBody UserInfoDeleteVo userInfoDeleteVo, BindingResult bindingResult) {
        // 调用业务层方法
        try {
            boolean flag = userService.removeUserInfoById(userInfoDeleteVo, bindingResult);
            if (flag) {
                log.info("userinfo delete => removeUserInfo success");
                return new Result(HttpStatus.OK.value(), "客服信息删除成功");
            } else {
                log.error("userinfo delete => removeUserInfo fail");
                return new Result(HttpStatus.NOT_FOUND.value(), "客服信息删除失败");
            }
        } catch (BusinessException e) {
            throw new BusinessException(e.getCode(), e.getMessage(), e.getData());
        } catch (Exception e) {
            throw new SystemException(Code.SYSTEM_ERR, e.getMessage(), e);
        }
    }

    // 修改客服人员信息
    @PostMapping("/update")
    public Result updateUserInfo(@Validated @RequestBody UserInfoUpdateVo userInfoUpdateVo, BindingResult bindingResult) {
        // 调用业务层方法
        try {
            boolean flag = userService.updateUserInfo(userInfoUpdateVo, bindingResult);
            if (flag) {
                log.info("userinfo update => updateUserInfo success");
                return new Result(HttpStatus.OK.value(), "客服信息修改成功");
            } else {
                log.error("userinfo update => updateUserInfo fail");
                return new Result(HttpStatus.NOT_FOUND.value(), "客服信息修改失败");
            }
        } catch (BusinessException e) {
            throw new BusinessException(e.getCode(), e.getMessage(), e.getData());
        } catch (Exception e) {
            throw new SystemException(Code.SYSTEM_ERR, e.getMessage(), e);
        }
    }

    // 查询单个客服人员信息
    @PostMapping("/getById")
    public Result getUserInfoById(@Validated @RequestBody UserInfoGetVo userInfoGetVo, BindingResult bindingResult) {
        // 调用业务层方法
        try {
            UserInfoGetVo userInfo = userService.getUserInfoById(userInfoGetVo, bindingResult);
            if (userInfo != null) {
                log.info("userinfo query => getUserInfoById success");
                return new Result(HttpStatus.OK.value(), "客服信息查询成功", userInfo);
            } else {
                log.error("userinfo query => getUserInfoById fail");
                return new Result(HttpStatus.NOT_FOUND.value(), "客服信息查询失败");
            }
        } catch (BusinessException e) {
            throw new BusinessException(e.getCode(), e.getMessage(), e.getData());
        } catch (Exception e) {
            throw new SystemException(Code.SYSTEM_ERR, e.getMessage(), e);
        }
    }

    // 查询所有客服人员信息
    @PostMapping("/getAll")
    public Result getUserInfoAll() {
        // 调用业务层方法
        try {
            List<UserInfoGetVo> userInfos = userService.getUserInfoAll();
            if (userInfos != null) {
                log.info("userinfo query => getUserInfoAll success");
                return new Result(HttpStatus.OK.value(), "客服信息查询成功", userInfos);
            } else {
                log.error("userinfo query => getUserInfoAll fail");
                return new Result(HttpStatus.NOT_FOUND.value(), "客服信息查询失败");
            }
        } catch (Exception e) {
            throw new SystemException(Code.SYSTEM_ERR, e.getMessage());
        }
    }
}
