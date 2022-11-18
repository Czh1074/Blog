package com.chenzhihui.blog.config;

import com.chenzhihui.blog.pojo.TencentCosPropertiesPicture;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tencent")
public class TencentCosConfig {

    public static final String COS_IMAGE = "image";

    @Autowired
    TencentCosPropertiesPicture tencentCosPropertiesPicture;

    @Bean
    @Qualifier(COS_IMAGE)
    @Primary
    public COSClient getCoSClient4Picture() {
        //初始化用户身份信息
        COSCredentials cosCredentials = new BasicCOSCredentials(tencentCosPropertiesPicture.getSecretId(),tencentCosPropertiesPicture.getSecretKey());
        //设置bucket区域，
        //clientConfig中包含了设置region
        ClientConfig clientConfig = new ClientConfig(new Region(tencentCosPropertiesPicture.getRegionId()));
        //生成cos客户端
        COSClient cosClient = new COSClient(cosCredentials,clientConfig);
        return  cosClient;

    }


}


