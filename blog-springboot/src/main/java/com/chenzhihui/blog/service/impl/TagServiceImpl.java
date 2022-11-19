package com.chenzhihui.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.generator.config.INameConvert;
import com.chenzhihui.blog.dto.TagBackDTO;
import com.chenzhihui.blog.dto.TagDTO;
import com.chenzhihui.blog.mapper.ArticleTagMapper;
import com.chenzhihui.blog.pojo.ArticleTag;
import com.chenzhihui.blog.pojo.Category;
import com.chenzhihui.blog.pojo.Tag;
import com.chenzhihui.blog.mapper.TagMapper;
import com.chenzhihui.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihui.blog.util.BeanCopyUtils;
import com.chenzhihui.blog.util.PageUtils;
import com.chenzhihui.blog.vo.PageResult;
import com.chenzhihui.blog.vo.TagVO;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Override
    public PageResult<TagDTO> listTags() {
        List<Tag> tagList = tagMapper.selectList(null);
        List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagList,TagDTO.class);
        Integer count = tagMapper.selectCount(null);
        return new PageResult<>(tagDTOList,count);
    }

    @Override
    public PageResult<TagBackDTO> tagBackList(TagVO tagVO) {
        // 查询标签数目
        Integer count = tagMapper.selectCount(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(tagVO.getTagName()),Tag::getTagName,tagVO.getTagName()));
        tagVO.setCurrent((tagVO.getCurrent()-1)*10);
        Page<Tag> tagPage = new Page<>(tagVO.getCurrent(),tagVO.getSize());
        List<TagBackDTO> tagBackDTOList = tagMapper.tagBackList(tagVO);
        return new PageResult<>(tagBackDTOList,count);
    }

    @Override
    public void addOrEditTags(TagVO tagVO) {
        // 通过tagId来判断是新增还是修改
        if(tagVO.getTagId() != null){
            // 判断旧的需不需要删除(将要修改的名字是不是属于旧的一个)
            Tag isOldTag = tagMapper.selectOne(new LambdaQueryWrapper<Tag>()
                    .eq(Tag::getTagName,tagVO.getTagName()));
            if(!Objects.isNull(isOldTag)){
                // 替换article-tag中的相关信息：即替换tagId
                List<ArticleTag> articleTagList = articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>()
                        .eq(ArticleTag::getTagId,tagVO.getTagId()));
                for(int i = 0; i < articleTagList.size(); i++){
                    articleTagList.get(i).setTagId(isOldTag.getTagId());
                    articleTagMapper.updateById(articleTagList.get(i));
                }
                // 删除原有标签
                tagMapper.deleteById(tagVO.getTagId());
            }else{
                // 属于只要修改tagName就行
                // 修改 -> 判断旧的需不需删除
                Tag tag = tagMapper.selectById(tagVO.getTagId());
                tag.setTagName(tagVO.getTagName());
                tag.setUpdateTime(new Date());
                tagMapper.updateById(tag);
            }
        }else{
            // 新增
            Tag tag = new Tag();
            tag.setTagName(tagVO.getTagName());
            tag.setCreateTime(new Date());
            tagMapper.insert(tag);
        }
    }

    /**
     * 删除标签
     *
     * @param tagId 标签id
     */
    @Override
    public Integer deleteTagById(Integer tagId) {
        // 判断当前是否有其他文章使用该标签，如果有则不能删除
        Integer count = articleTagMapper.selectCount(new LambdaQueryWrapper<ArticleTag>()
                .eq(ArticleTag::getTagId,tagId));
        if(count < 1){
            // 只有该文章使用，或是没人使用，删除
            tagMapper.deleteById(tagId);
        }
        return count;
    }
}
