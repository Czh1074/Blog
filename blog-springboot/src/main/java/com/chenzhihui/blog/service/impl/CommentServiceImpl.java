package com.chenzhihui.blog.service.impl;

import com.chenzhihui.blog.pojo.Comment;
import com.chenzhihui.blog.mapper.CommentMapper;
import com.chenzhihui.blog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
