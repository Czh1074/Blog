package com.chenzhihui.blog.service;

import com.chenzhihui.blog.dto.ResourceDTO;
import com.chenzhihui.blog.pojo.Resource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenzhihui.blog.vo.ConditionVO;
import com.chenzhihui.blog.vo.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
public interface ResourceService extends IService<Resource> {

    /**
     * 查看资源列表
     *
     * @param conditionVO keywords
     * @return {@link Result < ResourceDTO >} 资源列表
     */
    List<ResourceDTO> listResources(ConditionVO conditionVO);
}
