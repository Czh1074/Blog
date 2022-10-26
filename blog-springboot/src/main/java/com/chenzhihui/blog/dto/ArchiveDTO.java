package com.chenzhihui.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.cms.TimeStampAndCRL;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 归档文章展示
 *
 * @Author: ChenZhiHui
 * @DateTime: 2022/10/19 18:08
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveDTO {

    /**
     * id
     */
    private Integer articleId;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 发表时间
     */
    private Date createTime;


}
