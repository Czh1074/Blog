package com.chenzhihui.blog.exception;


import com.chenzhihui.blog.enums.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.chenzhihui.blog.enums.StatusCodeEnum.FAIL;

/**
 * 业务异常
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/10/23 21:16
 **/
@Getter
@AllArgsConstructor
public class BizException extends RuntimeException {

    /**
     * 错误码
     */
    private Integer code = FAIL.getCode();

    /**
     * 错误信息
     */
    private final String message;

    public BizException(String message) {
        this.message = message;
    }

    public BizException(StatusCodeEnum statusCodeEnum) {
        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getDesc();
    }


}