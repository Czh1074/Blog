package com.chenzhihui.blog.service;

import com.chenzhihui.blog.dto.MenuDTO;
import com.chenzhihui.blog.dto.UserMenuDTO;
import com.chenzhihui.blog.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihui.blog.vo.ConditionVO;

import java.util.List;

/**
 * <p>
 *  菜单服务
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
public interface MenuService extends IService<Menu> {

    /**
     * 查看菜单列表
     *
     * @param conditionVO 条件
     * @return 菜单列表
     */
    List<MenuDTO> listMenus(ConditionVO conditionVO);

    /**
     * 查看用户菜单
     *
     * @return 菜单列表
     */
    List<UserMenuDTO> listUserMenus();
}
