package com.chenzhihui.blog.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_auth")
public class UserAuth{

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_auth_id", type = IdType.AUTO)
    private Integer userAuthId;

    /**
     * 用户信息id
     */
    private Integer userInfoId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录类型
     */
    private Integer loginType;

    /**
     * 用户登录ip
     */
    private String ipAddress;

    /**
     * ip来源
     */
    private String ipSource;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;


}
