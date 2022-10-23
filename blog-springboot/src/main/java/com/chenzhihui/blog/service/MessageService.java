package com.chenzhihui.blog.service;

import com.chenzhihui.blog.dto.MessageDTO;
import com.chenzhihui.blog.pojo.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihui.blog.vo.MessageVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
public interface MessageService extends IService<Message> {

    /**
     * 查看留言列表
     *
     * @return {@link List<MessageDTO>} 留言列表
     * */
    List<MessageDTO> listMessage();

    void saveMessage(MessageVO messageVO);
}
