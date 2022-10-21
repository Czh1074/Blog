package com.chenzhihui.blog.service.impl;

import com.chenzhihui.blog.pojo.UserInfo;
import com.chenzhihui.blog.mapper.UserInfoMapper;
import com.chenzhihui.blog.service.UserInfoService;
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
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
