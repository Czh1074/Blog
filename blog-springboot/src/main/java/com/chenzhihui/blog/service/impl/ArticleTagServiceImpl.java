package com.chenzhihui.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihui.blog.mapper.ArticleTagMapper;
import com.chenzhihui.blog.pojo.ArticleTag;
import com.chenzhihui.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * 文章标签服务
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/11/13 16:53
 **/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
