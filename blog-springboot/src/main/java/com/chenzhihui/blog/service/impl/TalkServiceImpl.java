package com.chenzhihui.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chenzhihui.blog.dto.TalkDTO;
import com.chenzhihui.blog.pojo.Talk;
import com.chenzhihui.blog.mapper.TalkMapper;
import com.chenzhihui.blog.service.TalkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihui.blog.util.CommonUtils;
import com.chenzhihui.blog.util.PageUtils;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.Result;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.chenzhihui.blog.enums.ArticleStatusEnum.PUBLIC;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@Service
public class TalkServiceImpl extends ServiceImpl<TalkMapper, Talk> implements TalkService {

    @Resource
    private TalkMapper talkMapper;

    /**
     * 查看首页说说: 注：首页部分的说说轮播图
     *
     * @return {@link Result <String>}
     */
    @Override
    public List<String> listHomeTalks() {
//        List<String> talkList = talkMapper.selectList(new LambdaQueryWrapper<Talk>()
//                .eq(Talk::getStatus, PUBLIC.getStatus())
//                .orderByDesc(Talk::getIsTop)
//                .orderByDesc(Talk::getTalkId)
//                .last("limit 10"))
//                .stream()
//                .map(item -> item.getContent().length() > 200 ? HTMLUtils.deleteHMTLTag(item.getContent().substring(0, 200)) : HTMLUtils.deleteHMTLTag(item.getContent()))
//                .collect(Collectors.toList());
//
        return null;
    }

    /**
     * 查看说说列表
     *
     * @return {@link Result<TalkDTO>}
     */
    @Override
    public PageResult<TalkDTO> listTalks(Long current, Long size) {
        // 查询说说总量
        Integer count = talkMapper.selectCount( new LambdaQueryWrapper<Talk>()
                .eq(Talk::getStatus,PUBLIC.getStatus()));
        if(count == 0) return new PageResult<>();
        // 分页查询说说
        List<TalkDTO> talkDTOList = talkMapper.listTalks((current-1)*10,PageUtils.getSize());
        // 查询说说评论量
        // todo: 未完成说说评论量

        // 查询说说点赞量
        // todo: 说说点赞量

        // todo: 图片格式转换不懂
        // 封装数据
        talkDTOList.forEach(item ->{
            if(Objects.nonNull(item.getImages())){
                item.setImgList(CommonUtils.castList(JSON.parseObject(item.getImages(),List.class),String.class));
            }
        });

        return new PageResult<>(talkDTOList, count);
    }


    /**
     * 根据id查看说说详细信息
     *
     * @param talkId
     * @return {@link TalkDTO}
     */
    @Override
    public TalkDTO getTalkById(Integer talkId) {
        TalkDTO talkDTO = talkMapper.getTalkById(talkId);
        return talkDTO;
    }
}
