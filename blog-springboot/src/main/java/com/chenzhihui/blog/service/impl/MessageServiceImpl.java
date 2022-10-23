package com.chenzhihui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chenzhihui.blog.dto.MessageDTO;
import com.chenzhihui.blog.pojo.Message;
import com.chenzhihui.blog.mapper.MessageMapper;
import com.chenzhihui.blog.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihui.blog.util.BeanCopyUtils;
import com.chenzhihui.blog.vo.MessageVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import static com.chenzhihui.blog.constant.CommonConst.TRUE;
import java.util.List;

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

    @Resource
    private MessageMapper messageMapper;

    /**
     * 查看留言列表
     *
     * @return {@link List<MessageDTO>} 留言列表
     * */
    @Override
    public List<MessageDTO> listMessage() {
        List<Message> listMessage = messageMapper.selectList(new LambdaQueryWrapper<Message>()
                .eq(Message::getIsReview,TRUE));
        return BeanCopyUtils.copyList(listMessage,MessageDTO.class);
    }

    @Override
    public void saveMessage(MessageVO messageVO) {

    }


}
