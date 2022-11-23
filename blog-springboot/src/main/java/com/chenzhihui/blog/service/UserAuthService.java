package com.chenzhihui.blog.service;

import com.chenzhihui.blog.pojo.UserAuth;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihui.blog.vo.Result;
import com.chenzhihui.blog.vo.UserVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
public interface UserAuthService extends IService<UserAuth> {

    /**
     * 发送邮箱验证码
     *
     * @param username 用户名
     * @return {@link Result <>}
     */
    void sendCode(String username);


    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return {@link Result<>}
     */
    void register(UserVO user);


    /**
     * 修改密码
     *
     * @param user 用户信息
     * @return {@link Result<>}
     */
    void updatePassword(UserVO user);

    Integer check(UserVO userVO);
}
