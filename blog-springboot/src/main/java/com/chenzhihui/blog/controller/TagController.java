package com.chenzhihui.blog.controller;


import com.chenzhihui.blog.dto.TagDTO;
import com.chenzhihui.blog.service.TagService;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@RestController
//@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;


    /**
     * 查看标签列表
     *
     * @return {@link TagDTO} 标签列表
     * */
    @GetMapping("/tags/listTags")
    public Result<PageResult<TagDTO>> listTags(){
        return Result.ok(tagService.listTags());
    }

}

