package com.chenzhihui.blog.controller;


import com.chenzhihui.blog.dto.TalkDTO;
import com.chenzhihui.blog.service.TalkService;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  说说控制器
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@RestController
//@RequestMapping("/talk")
public class TalkController {

    @Autowired
    private TalkService talkService;

    /**
     * todo：未实现首页说说轮播图，涉及到htmlutils
     *
     * 查看首页说说: 注：首页部分的说说轮播图
     *
     * @return {@link Result<String>}
     */
    @ApiOperation(value = "查看首页说说")
    @GetMapping("/talk/homeTalks")
    public Result<List<String>> listHomeTalks() {
        return Result.ok(talkService.listHomeTalks());
    }

    /**
     * 查看说说列表
     *
     * @return {@link Result<TalkDTO>}
     */
    @ApiOperation(value = "查看说说列表")
    @GetMapping("/talks/listTalks/{current}/{size}")
    public Result<PageResult<TalkDTO>> listTalks(@PathVariable("current") Long current,@PathVariable("size") Long size) {
        return Result.ok(talkService.listTalks(current,size));
    }

    /**
     * 根据id查看说说详细信息
     *
     * @param talkId
     * @return {@link TalkDTO}
     */
    @ApiOperation(value = "通过talk_id查看说说详情")
    @GetMapping("/talk/getTalkById/{talkId}")
    public Result<TalkDTO> getTalkById(@PathVariable("talkId") Integer talkId){
        return Result.ok(talkService.getTalkById(talkId));
    }
}

