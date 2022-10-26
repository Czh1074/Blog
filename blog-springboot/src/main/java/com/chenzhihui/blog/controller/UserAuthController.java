package com.chenzhihui.blog.controller;


import com.chenzhihui.blog.annotation.AccessLimit;
import com.chenzhihui.blog.service.UserAuthService;
import com.chenzhihui.blog.vo.Result;
import com.chenzhihui.blog.vo.UserVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  用户账号 前端控制器
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@RestController
//@RequestMapping("/userAuth")
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;


    /**
     * 发送邮箱验证码
     *
     * @param username 用户名
     * @return {@link Result<>}
     */
    @AccessLimit(seconds = 60, maxCount = 1)
    @ApiOperation(value = "发送邮箱验证码")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String")
    @GetMapping("/userAuth/code/{username}")
    public Result<?> sendCode(@PathVariable("username") String username) {
        userAuthService.sendCode(username);
        return Result.ok();
    }

    /**
     * todo: 未测试
     * 用户注册
     *
     * @param user 用户信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/userAuth/register")
    public Result<?> register(@Valid @RequestBody UserVO user) {
        userAuthService.register(user);
        return Result.ok();
    }

    /**
     * todo：未测试
     * 修改密码
     *
     * @param user 用户信息
     * @return {@link Result<>}
     */
    @ApiOperation(value = "修改密码")
    @PutMapping("/userAuth/password")
    public Result<?> updatePassword(@Valid @RequestBody UserVO user) {
        userAuthService.updatePassword(user);
        return Result.ok();
    }


}

