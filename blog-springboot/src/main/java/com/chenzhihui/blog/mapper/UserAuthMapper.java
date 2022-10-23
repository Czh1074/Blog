package com.chenzhihui.blog.mapper;

import com.chenzhihui.blog.pojo.UserAuth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenzhihui.blog.vo.Result;
import com.chenzhihui.blog.vo.UserVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
public interface UserAuthMapper extends BaseMapper<UserAuth> {


    /**
     * 修改密码
     *
     * @param user 用户信息
     * @return {@link Result <>}
     */
    void updatePasswordById(UserVO user);
}
