package com.chenzhihui.blog.controller;


import com.chenzhihui.blog.dto.PhotoAlbumDTO;
import com.chenzhihui.blog.service.PhotoAlbumService;
import com.chenzhihui.blog.vo.Result;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 相册 前端控制器
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@RestController
@RequestMapping("/photoAlbum")
public class PhotoAlbumController {

    @Autowired
    private PhotoAlbumService photoAlbumService;


    /**
     * 获取相册列表
     *
     * @return {@link List<PhotoAlbumDTO>} 相册列表
     * */
    @GetMapping("/listPhotoAlbum")
    public Result<List<PhotoAlbumDTO>> listPhotoAlbum(){
        return Result.ok(photoAlbumService.listPhotoAlbum());
    }
}

