package com.chenzhihui.blog.service.impl;

import com.chenzhihui.blog.pojo.Message;
import com.chenzhihui.blog.mapper.MessageMapper;
import com.chenzhihui.blog.service.MessageService;
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
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}
