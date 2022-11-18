package com.chenzhihui.blog.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 腾讯云cos上传配置
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/11/18 09:06
 **/
@Data
@Component
@ConfigurationProperties(prefix = "tencent.cos")
public class TencentCosPropertiesPicture{

    private String appId;
    private String secretId;
    private String secretKey;
    private String bucketName;
    private String regionId;
    private String baseUrl;

}
