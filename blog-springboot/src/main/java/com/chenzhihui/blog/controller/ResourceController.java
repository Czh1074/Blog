package com.chenzhihui.blog.controller;


import com.chenzhihui.blog.dto.ResourceDTO;
import com.chenzhihui.blog.service.ResourceService;
import com.chenzhihui.blog.vo.ConditionVO;
import com.chenzhihui.blog.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  借口管理-控制器
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@Api("资源模块")
@RestController
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    /**
     * 查看资源列表
     *
     * @param conditionVO keywords
     * @return {@link Result<ResourceDTO>} 资源列表
     */
    @ApiOperation(value = "查看资源列表")
    @GetMapping("/admin/resources")
    public Result<List<ResourceDTO>> listResources(ConditionVO conditionVO) {
        return Result.ok(resourceService.listResources(conditionVO));
    }
}

