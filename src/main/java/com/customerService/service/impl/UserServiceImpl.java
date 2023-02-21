package com.customerService.service.impl;

import com.customerService.entity.UserInfo;
import com.customerService.entity.UserInfoExpand;
import com.customerService.vo.UserInfoDeleteVo;
import com.customerService.vo.UserInfoGetVo;
import com.customerService.vo.UserInfoSaveVo;
import com.customerService.vo.UserInfoUpdateVo;
import com.customerService.exception.BusinessException;
import com.customerService.mapper.UserMapper;
import com.customerService.service.UserService;
import com.customerService.util.Code;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    // 添加客服信息（客服信息表和客服信息扩展表）
    @Override
    public boolean saveUserInfo(UserInfoSaveVo userInfoSaveVo, BindingResult bindingResult) {

        // 请求参数校验
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            // 取出所有校验不通过的信息
            List<String> collect = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            log.error("userinfo save => saveUserInfo error 请求参数{}校验不通过: {}", userInfoSaveVo, collect);
            throw new BusinessException(Code.BUSINESS_ERR, "请求参数校验不通过", collect);
        }

        // 判断数据库中该用户是否存在
        boolean flag = getUserInfoByIdNumber(userInfoSaveVo.getIdNumber());
        if (flag) {
            log.error("userinfo save => saveUserInfo error 该身份证号{}对应的用户信息已存在", userInfoSaveVo.getIdNumber());
            return false;
        }

        // 添加客服信息表相关信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userInfoSaveVo.getUsername());
        // 密码进行MD5盐值加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userInfo.setPassword(passwordEncoder.encode(userInfoSaveVo.getPassword()));
        userInfo.setCompany(userInfoSaveVo.getCompany());
        userInfo.setNickname(userInfoSaveVo.getNickname());
        userInfo.setIdNumber(userInfoSaveVo.getIdNumber());
        userInfo.setCreateTime(LocalDateTime.now());
        userInfo.setUpdateTime(LocalDateTime.now());
        userInfo.setDeleted(0);
        int saveCount1 = userMapper.saveUserInfo(userInfo);

        // 判断添加客服信息表相关信息是否成功
        if (saveCount1 == 0) {
            log.error("userinfo save => saveUserInfo error 客服信息表数据添加失败");
            return false;
        }

        // 添加客服信息扩展表相关信息
        UserInfoExpand userInfoExpand = new UserInfoExpand();
        // 查询刚添加的客服信息表id
        Long userInfoId = userMapper.getUserInfoIdByIdNumber(userInfoSaveVo.getIdNumber());
        userInfoExpand.setInfoId(userInfoId);
        userInfoExpand.setExternalNickname(userInfoSaveVo.getExternalNickname());
        userInfoExpand.setEmail(userInfoSaveVo.getEmail());
        userInfoExpand.setPhone(userInfoSaveVo.getPhone());
        userInfoExpand.setCountry(userInfoSaveVo.getCountry());
        userInfoExpand.setDefaultLanguage(userInfoSaveVo.getDefaultLanguage());
        int saveCount2 = userMapper.saveUserInfoExpand(userInfoExpand);

        // 判断添加客服信息扩展表相关信息是否成功
        if (saveCount2 == 0) {
            log.error("userinfo save => saveUserInfo error 客服信息扩展表数据添加失败");
            return false;
        }

        return true;
    }

    // 删除客服信息表（根据客服信息表id，逻辑删除）
    @Override
    public boolean removeUserInfoById(UserInfoDeleteVo userInfoDeleteVo, BindingResult bindingResult) {

        // 请求参数校验
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            // 取出所有校验不通过的信息
            List<String> collect = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            log.error("userinfo delete => removeUserInfoById error 请求参数{}校验不通过: {}", userInfoDeleteVo, collect);
            throw new BusinessException(Code.BUSINESS_ERR, "请求参数校验不通过", collect);
        }

        // 判断该客服信息是否已经被逻辑删除
        int deleted = userMapper.getIfExists(userInfoDeleteVo.getId());
        if (deleted == 1) {
            log.error("userinfo delete => removeUserInfoById error 该客服信息已被逻辑删除，不可重复删除");
            return false;
        }

        // 根据客服id逻辑删除该客服相关信息
        int removeCount = userMapper.removeById(userInfoDeleteVo.getId());
        if (removeCount == 0) {
            log.error("userinfo delete => removeUserInfoById error 客服信息表数据删除失败");
            return false;
        }

        return true;
    }

    // 修改客服信息表和客服信息扩展表相关信息
    @Override
    public boolean updateUserInfo(UserInfoUpdateVo userInfoUpdateVo, BindingResult bindingResult) {

        // 请求参数校验
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            // 取出所有校验不通过的信息
            List<String> collect = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            log.error("userinfo update => updateUserInfo error 请求参数{}校验不通过: {}", userInfoUpdateVo, collect);
            throw new BusinessException(Code.BUSINESS_ERR, "请求参数校验不通过", collect);
        }

        // 判断该客服信息是否已经被逻辑删除
        int deleted = userMapper.getIfExists(userInfoUpdateVo.getId());
        if (deleted == 1) {
            log.error("userinfo update => updateUserInfo error 该客服信息已被逻辑删除，不可修改");
            return false;
        }

        // 修改客服信息表相关信息
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userInfoUpdateVo.getId());
        userInfo.setUsername(userInfoUpdateVo.getUsername());
        // 密码进行MD5盐值加密
        if (userInfoUpdateVo.getPassword() != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userInfo.setPassword(passwordEncoder.encode(userInfoUpdateVo.getPassword()));
        }
        userInfo.setCompany(userInfoUpdateVo.getCompany());
        userInfo.setNickname(userInfoUpdateVo.getNickname());
        userInfo.setIdNumber(userInfoUpdateVo.getIdNumber());
        userInfo.setUpdateTime(LocalDateTime.now());
        int updateCount1 = userMapper.updateUserInfo(userInfo);

        // 判断客服信息表相关信息是否修改成功
        if (updateCount1 == 0) {
            log.error("userinfo update => updateUserInfo error 客服信息表数据修改失败");
            return false;
        }

        // 修改客服信息扩展表相关信息
        UserInfoExpand userInfoExpand = new UserInfoExpand();
        userInfoExpand.setInfoId(userInfoUpdateVo.getId());
        userInfoExpand.setExternalNickname(userInfoUpdateVo.getExternalNickname());
        userInfoExpand.setEmail(userInfoUpdateVo.getEmail());
        userInfoExpand.setPhone(userInfoUpdateVo.getPhone());
        userInfoExpand.setCountry(userInfoUpdateVo.getCountry());
        userInfoExpand.setDefaultLanguage(userInfoUpdateVo.getDefaultLanguage());
        int updateCount2 = userMapper.updateUserInfoExpand(userInfoExpand);

        // 判断客服信息扩展表相关信息是否修改成功
        if (updateCount2 == 0) {
            log.error("userinfo update => updateUserInfo error 客服信息扩展表数据修改失败");
            return false;
        }

        return true;
    }

    // 查询客服信息表和客服信息扩展表中单个用户信息（根据客服信息表id）
    @Override
    public UserInfoGetVo getUserInfoById(UserInfoGetVo userInfoGetVo, BindingResult bindingResult) {

        // 请求参数校验
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            // 取出所有校验不通过的信息
            List<String> collect = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            log.error("userinfo query => getUserInfoById error 请求参数{}校验不通过: {}", userInfoGetVo, collect);
            throw new BusinessException(Code.BUSINESS_ERR, "请求参数校验不通过", collect);
        }

        // 判断该客服信息是否已经被逻辑删除
        int deleted = userMapper.getIfExists(userInfoGetVo.getId());
        if (deleted == 1) {
            log.error("userinfo query => getUserInfoById error 该客服信息已被逻辑删除，不可查询");
            return null;
        }

        // 根据客服id查询客服相关信息并封装（客服信息表和客服信息扩展表）
        UserInfo userInfo = userMapper.getUserInfoById(userInfoGetVo.getId());
        UserInfoExpand userInfoExpand = userMapper.getUserInfoExpandById(userInfoGetVo.getId());

        UserInfoGetVo userInfoDisplay = new UserInfoGetVo();
        userInfoDisplay.setId(userInfoGetVo.getId());
        userInfoDisplay.setUsername(userInfo.getUsername());
        userInfoDisplay.setPassword(userInfo.getPassword());
        userInfoDisplay.setCompany(userInfo.getCompany());
        userInfoDisplay.setNickname(userInfo.getNickname());
        userInfoDisplay.setIdNumber(userInfo.getIdNumber());
        // 日期显示格式
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        userInfoDisplay.setCreateTime(userInfo.getCreateTime().format(pattern));
        userInfoDisplay.setUpdateTime(userInfo.getUpdateTime().format(pattern));
        userInfoDisplay.setExternalNickname(userInfoExpand.getExternalNickname());
        userInfoDisplay.setEmail(userInfoExpand.getEmail());
        userInfoDisplay.setPhone(userInfoExpand.getPhone());
        userInfoDisplay.setCountry(userInfoExpand.getCountry());
        userInfoDisplay.setDefaultLanguage(userInfoExpand.getDefaultLanguage());
        return userInfoDisplay;
    }

    // 查询客服信息表和客服信息扩展表中所有用户信息
    @Override
    public List<UserInfoGetVo> getUserInfoAll() {

        // 查询客服信息表和客服信息扩展表中所有用户信息
        List<UserInfo> userInfos = userMapper.getUserInfoAll();
        List<UserInfoExpand> userInfoExpands = userMapper.getUserInfoExpandAll();

        // 遍历客服信息集合和客服扩展信息集合，根据客服id进行数据组装
        List<UserInfoGetVo> userInfoDisplays = new ArrayList<>();
        for (UserInfo userInfo : userInfos) {
            for (UserInfoExpand userInfoExpand : userInfoExpands) {
                // 获取到客服信息id
                Long userInfoId = userInfo.getId();
                // 根据客服信息id来组装数据
                if (userInfoExpand != null && userInfoId.equals(userInfoExpand.getInfoId())) {
                    UserInfoGetVo userInfoDisplay = new UserInfoGetVo();
                    userInfoDisplay.setId(userInfoId);
                    userInfoDisplay.setUsername(userInfo.getUsername());
                    userInfoDisplay.setPassword(userInfo.getPassword());
                    userInfoDisplay.setCompany(userInfo.getCompany());
                    userInfoDisplay.setNickname(userInfo.getNickname());
                    userInfoDisplay.setIdNumber(userInfo.getIdNumber());
                    // 日期显示格式
                    DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    userInfoDisplay.setCreateTime(userInfo.getCreateTime().format(pattern));
                    userInfoDisplay.setUpdateTime(userInfo.getUpdateTime().format(pattern));
                    userInfoDisplay.setExternalNickname(userInfoExpand.getExternalNickname());
                    userInfoDisplay.setEmail(userInfoExpand.getEmail());
                    userInfoDisplay.setPhone(userInfoExpand.getPhone());
                    userInfoDisplay.setCountry(userInfoExpand.getCountry());
                    userInfoDisplay.setDefaultLanguage(userInfoExpand.getDefaultLanguage());

                    userInfoDisplays.add(userInfoDisplay);
                }
            }
        }
        return userInfoDisplays;
    }

    // 查询客服信息表中某用户是否存在（根据身份证号idNumber查询）
    @Override
    public boolean getUserInfoByIdNumber(String idNumber) {
        UserInfo userInfo = userMapper.getUserInfoByIdNumber(idNumber);
        return userInfo != null;
    }
}
