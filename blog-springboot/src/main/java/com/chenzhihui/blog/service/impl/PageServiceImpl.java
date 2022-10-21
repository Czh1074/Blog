package com.chenzhihui.blog.service.impl;

import com.chenzhihui.blog.pojo.Page;
import com.chenzhihui.blog.mapper.PageMapper;
import com.chenzhihui.blog.service.PageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 页面 服务实现类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@Service
public class PageServiceImpl extends ServiceImpl<PageMapper, Page> implements PageService {

}
