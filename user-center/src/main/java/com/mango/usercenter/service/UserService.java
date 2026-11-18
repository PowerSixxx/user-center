package com.mango.usercenter.service;

import com.mango.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author Baowen
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2025-12-05 00:48:17
 */
public interface UserService extends IService<User> {

    /**
     * User Register-
     *
     * @param userAccount   user account
     * @param userPassword  user password
     * @param checkPassword double-check password
     * @param noteCode      note code
     * @param username      username
     * @return new user ID
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String noteCode, String username);

    /**
     * User Login
     *
     * @param userAccount  user account
     * @param userPassword user passwords
     * @return the desensitized user information
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * user desensitization
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * user logout
     * 
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);
}
