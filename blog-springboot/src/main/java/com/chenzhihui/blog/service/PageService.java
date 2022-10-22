package com.chenzhihui.blog.service;

import com.chenzhihui.blog.pojo.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihui.blog.vo.PageVO;

import java.util.List;

/**
 * <p>
 * 页面 服务类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
public interface PageService extends IService<Page> {

    /**
     * 获取页面列表
     *
     * @return {@link List<PageVO>} 页面列表
     */
    List<PageVO> listPages();
}
