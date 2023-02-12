package com.customerService.service.impl;

import com.customerService.domain.UserInfo;
import com.customerService.domain.UserInfoDisplay;
import com.customerService.domain.UserInfoExpand;
import com.customerService.domain.UserInfoSum;
import com.customerService.mapper.UserMapper;
import com.customerService.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    // 添加客服信息表和客服信息扩展表相关信息
    @Override
    public boolean saveUserInfo(UserInfoSum userInfoSum) {

        // 添加客服信息表相关信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(userInfoSum.getUsername());
        userInfo.setPassword(userInfoSum.getPassword());
        userInfo.setCompany(userInfoSum.getCompany());
        userInfo.setNickname(userInfoSum.getNickname());
        userInfo.setCreateTime(userInfoSum.getCreateTime());
        userInfo.setUpdateTime(userInfoSum.getUpdateTime());
        userInfo.setDeleted(0);
        int saveCount1 = userMapper.saveUserInfo(userInfo);

        // 判断添加客服信息表相关信息是否成功
        if (saveCount1 == 0) {
            log.info("客服信息表数据添加失败");
            return false;
        } else {
            log.info("客服信息表数据添加成功");
        }

        // 查询刚添加的客服信息主键id
        Integer userInfoId = userMapper.getUserInfoIdByUsername(userInfoSum.getUsername());

        // 添加客服信息扩展表相关信息
        UserInfoExpand userInfoExpand = new UserInfoExpand();
        userInfoExpand.setInfoId(userInfoId);
        userInfoExpand.setExternalNickname(userInfoSum.getExternalNickname());
        userInfoExpand.setEmail(userInfoSum.getEmail());
        userInfoExpand.setPhone(userInfoSum.getPhone());
        userInfoExpand.setCountry(userInfoSum.getCountry());
        userInfoExpand.setDefaultLanguage(userInfoSum.getDefaultLanguage());
        int saveCount2 = userMapper.saveUserInfoExpand(userInfoExpand);

        // 判断添加客服信息扩展表相关信息是否成功
        if (saveCount2 == 0) {
            log.info("客服信息扩展表数据添加失败");
            return false;
        } else {
            log.info("客服信息扩展表数据添加成功");
        }

        return true;
    }

    // 删除客服信息表（根据客服信息表id，逻辑删除）
    @Override
    public boolean removeById(Integer userInfoId) {

        // 判断该客服信息数据是否已经逻辑删除
        int ifExists = userMapper.getIfExists(userInfoId);

        if (ifExists == 0) {
            log.info("该客服信息存在，可逻辑删除");
            int removeCount = userMapper.removeById(userInfoId);
            if (removeCount == 0) {
                log.info("客服信息表数据删除失败");
                return false;
            }
        } else {
            log.info("该客服信息不存在，不可逻辑删除");
            return false;
        }

        log.info("客服信息表数据删除成功");
        return true;
    }

    // 修改客服信息表和客服信息扩展表相关信息
    @Override
    public boolean updateUserInfo(UserInfoSum userInfoSum) {

        // 获取用户信息id
        int userInfoId = userInfoSum.getId();

        // 判断该客服信息数据是否已经逻辑删除
        int ifExists = userMapper.getIfExists(userInfoId);

        if (ifExists == 0) {
            log.info("该客服信息存在，可修改");

            // 修改客服信息表相关信息
            UserInfo userInfo = new UserInfo();
            userInfo.setId(userInfoId);
            userInfo.setUsername(userInfoSum.getUsername());
            userInfo.setPassword(userInfoSum.getPassword());
            userInfo.setCompany(userInfoSum.getCompany());
            userInfo.setNickname(userInfoSum.getNickname());
            userInfo.setUpdateTime(userInfoSum.getUpdateTime());
            int updateCount1 = userMapper.updateUserInfo(userInfo);

            // 判断客服信息表相关信息是否修改成功
            if (updateCount1 == 0) {
                log.info("客服信息表数据修改失败");
                return false;
            } else {
                log.info("客服信息表数据修改成功");
            }

            // 修改客服信息扩展表相关信息
            UserInfoExpand userInfoExpand = new UserInfoExpand();
            userInfoExpand.setInfoId(userInfoId);
            userInfoExpand.setExternalNickname(userInfoSum.getExternalNickname());
            userInfoExpand.setEmail(userInfoSum.getEmail());
            userInfoExpand.setPhone(userInfoSum.getPhone());
            userInfoExpand.setCountry(userInfoSum.getCountry());
            userInfoExpand.setDefaultLanguage(userInfoSum.getDefaultLanguage());
            int updateCount2 = userMapper.updateUserInfoExpand(userInfoExpand);

            // 判断客服信息扩展表相关信息是否修改成功
            if (updateCount2 == 0) {
                log.info("客服信息扩展表数据修改失败");
                return false;
            } else {
                log.info("客服信息扩展表数据修改成功");
            }

            return true;
        } else {
            log.info("该客服信息已经逻辑删除，不可修改");
            return false;
        }
    }

    // 查询客服信息表和客服信息扩展表中单个用户信息（根据客服信息表id）
    @Override
    public UserInfoDisplay getUserInfoById(Integer userInfoId) {

        // 判断该客服信息数据是否已经逻辑删除
        int ifExists = userMapper.getIfExists(userInfoId);

        if (ifExists == 0) {
            log.info("该客服信息存在，可查询");

            // 查询客服信息表和客服信息扩展表相关信息并封装
            UserInfo userInfo = userMapper.getUserInfoById(userInfoId);
            UserInfoExpand userInfoExpand = userMapper.getUserInfoExpandById(userInfoId);

            UserInfoDisplay userInfoDisplay = new UserInfoDisplay();
            userInfoDisplay.setId(userInfoId);
            userInfoDisplay.setUsername(userInfo.getUsername());
            userInfoDisplay.setPassword(userInfo.getPassword());
            userInfoDisplay.setCompany(userInfo.getCompany());
            userInfoDisplay.setNickname(userInfo.getNickname());
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
        } else {
            log.info("该客服信息已经逻辑删除，不可查询");
            return null;
        }
    }

    // 查询客服信息表和客服信息扩展表中所有用户信息
    @Override
    public List<UserInfoDisplay> getUserInfoAll() {

        // 查询客服信息表中所有用户信息
        List<UserInfo> userInfos = userMapper.getUserInfoAll();

        // 遍历该集合，查询客服信息扩展表相关数据，将二者合并封装
        List<UserInfoDisplay> userInfoDisplays = new ArrayList<>();

        for (UserInfo userInfo : userInfos) {
            Integer userInfoId = userInfo.getId();
            UserInfoExpand userInfoExpand = userMapper.getUserInfoExpandById(userInfoId);

            UserInfoDisplay userInfoDisplay = new UserInfoDisplay();
            userInfoDisplay.setId(userInfoId);
            userInfoDisplay.setUsername(userInfo.getUsername());
            userInfoDisplay.setPassword(userInfo.getPassword());
            userInfoDisplay.setCompany(userInfo.getCompany());
            userInfoDisplay.setNickname(userInfo.getNickname());
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

        return userInfoDisplays;
    }

    // 查询客服信息表中单个用户信息（根据客服信息表的登录账号）
    @Override
    public boolean getUserInfoByUsername(String username) {
        UserInfo userInfo = userMapper.getUserInfoByUsername(username);
        if (userInfo == null) {
            return false;
        }
        return true;
    }
}
