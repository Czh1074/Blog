package com.chenzhihui.blog.service.impl;

import com.chenzhihui.blog.pojo.Page;
import com.chenzhihui.blog.mapper.PageMapper;
import com.chenzhihui.blog.service.PageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihui.blog.util.BeanCopyUtils;
import com.chenzhihui.blog.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    @Autowired
    public PageMapper pageMapper;

    /**
     * 获取页面列表
     *
     * @return {@link List<PageVO>} 页面列表
     */
    @Override
    public List<PageVO> listPages() {
        List<PageVO> pageVOList;
        // 查找缓存信息，不存在则从mysql中读取，更新缓存
        // todo: 没有实现从redis，而是直接从数据库查找
        pageVOList = BeanCopyUtils.copyList(pageMapper.selectList(null),PageVO.class);
        return pageVOList;
    }
}
