package com.chenzhihui.blog.controller;


import com.chenzhihui.blog.dto.UserMenuDTO;
import com.chenzhihui.blog.service.MenuService;
import com.chenzhihui.blog.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@RestController
public class MenuController {

    @Resource
    private MenuService menuService;

//    /**
//     * 查看当前用户菜单
//     *
//     * @return {@link Result<UserMenuDTO>} 菜单列表
//     */
//    @ApiOperation(value = "查看当前用户菜单")
//    @GetMapping("/admin/user/menus")
//    public Result<List<UserMenuDTO>> listUserMenus() {
//        return Result.ok(menuService.listUserMenus());
//    }
}

