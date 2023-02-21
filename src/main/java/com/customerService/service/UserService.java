package com.customerService.service;

import com.customerService.vo.UserInfoDeleteVo;
import com.customerService.vo.UserInfoGetVo;
import com.customerService.vo.UserInfoSaveVo;
import com.customerService.vo.UserInfoUpdateVo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;


@Transactional(rollbackFor = Exception.class)
public interface UserService {

    // 添加客服信息（客服信息表和客服信息扩展表）
    boolean saveUserInfo(UserInfoSaveVo userInfoSaveVo, BindingResult bindingResult);

    // 删除客服信息表（根据客服信息表id，逻辑删除）
    boolean removeUserInfoById(UserInfoDeleteVo userInfoDeleteVo, BindingResult bindingResult);

    // 修改客服信息表和客服信息扩展表相关信息
    boolean updateUserInfo(UserInfoUpdateVo userInfoUpdateVo, BindingResult bindingResult);

    // 查询客服信息表和客服信息扩展表中单个用户信息（根据客服信息表id）
    UserInfoGetVo getUserInfoById(UserInfoGetVo userInfoGetVo, BindingResult bindingResult);

    // 查询客服信息表和客服信息扩展表中所有用户信息
    List<UserInfoGetVo> getUserInfoAll();

    // 查询客服信息表中某用户是否存在（根据身份证号idNumber查询）
    boolean getUserInfoByIdNumber(String idNumber);
}
