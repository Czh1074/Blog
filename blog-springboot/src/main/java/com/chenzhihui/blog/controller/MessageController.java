package com.chenzhihui.blog.controller;


import com.chenzhihui.blog.annotation.AccessLimit;
import com.chenzhihui.blog.dto.MessageDTO;
import com.chenzhihui.blog.service.MessageService;
import com.chenzhihui.blog.vo.MessageVO;
import com.chenzhihui.blog.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  留言控制器
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@RestController
//@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * 查看留言列表
     *
     * @return {@link List<MessageDTO>} 留言列表
     * */
    @GetMapping("/message/listMessage")
    public Result<List<MessageDTO>> listMessage(){
        return Result.ok(messageService.listMessage());
    }

    /**
     * todo：添加留言 涉及到接口恶意防刷，redis
     * 添加留言
     *
     * @param messageVO 留言信息
     * @return {@link Result<>}
     */
    @AccessLimit(seconds = 60, maxCount = 1) //
    @ApiOperation(value = "添加留言")
    @PostMapping("/message/messages")
    public Result<?> saveMessage(@Valid @RequestBody MessageVO messageVO) {
        messageService.saveMessage(messageVO);
        return Result.ok();
    }


}

