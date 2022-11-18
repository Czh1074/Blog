package com.chenzhihui.blog.controller;


import com.chenzhihui.blog.annotation.AccessLimit;
import com.chenzhihui.blog.service.UserAuthService;
import com.chenzhihui.blog.vo.Result;
import com.chenzhihui.blog.vo.UserVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 用户登录
     *
     * @param username,password 用户信息
     * @return {@link Result<>}
     */
    @PostMapping("/admin/login")
    public Result<?> login(@PathParam("username") String username, @PathParam("password") String password){
        Map<String, Object> map = new HashMap<>();
        System.out.println("我被调用到了！！");
        map.put("token","admin-token-a");
        return Result.ok(map);
    }

    @GetMapping("/admin/info")
    public Result info(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("roles", "[admin]");
        map.put("introduction", "管理员");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        map.put("name", "Super Admin");
        return Result.ok(map);
    }

    @PostMapping("/admin/logout")
    public Result logout() {
        return Result.ok();
    }


}

