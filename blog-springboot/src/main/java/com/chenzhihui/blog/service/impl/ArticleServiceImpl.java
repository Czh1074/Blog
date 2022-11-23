package com.chenzhihui.blog.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenzhihui.blog.config.TencentCosConfig;
import com.chenzhihui.blog.dto.*;
import com.chenzhihui.blog.exception.BizException;
import com.chenzhihui.blog.mapper.ArticleTagMapper;
import com.chenzhihui.blog.mapper.CategoryMapper;
import com.chenzhihui.blog.mapper.TagMapper;
import com.chenzhihui.blog.pojo.*;
import com.chenzhihui.blog.mapper.ArticleMapper;
import com.chenzhihui.blog.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihui.blog.util.BeanCopyUtils;
import com.chenzhihui.blog.util.CommonUtils;
import com.chenzhihui.blog.util.PageUtils;
import com.chenzhihui.blog.util.UserUtils;
import com.chenzhihui.blog.vo.*;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.chenzhihui.blog.constant.CommonConst.FALSE;
import static com.chenzhihui.blog.constant.RedisPrefixConst.BLOG_VIEWS_COUNT;
import static com.chenzhihui.blog.enums.ArticleStatusEnum.DRAFT;
import static com.chenzhihui.blog.enums.ArticleStatusEnum.PUBLIC;
import static com.chenzhihui.blog.constant.CommonConst.ARTICLE_SET;
import static com.chenzhihui.blog.constant.RedisPrefixConst.ARTICLE_VIEWS_COUNT;


/**
 * <p>
 *  文章 - 服务实现类
 * </p>
 *
 * @author chenzhihui
 * @since 2022-10-19
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private HttpSession session;

    @Resource
    private RedisService redisService;

    @Resource
    private BlogInfoService blogInfoService;

    @Resource
    private TagService tagService;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private ArticleTagMapper articleTagMapper;

    @Resource
    private TencentCosPropertiesPicture tencentCosPropertiesPicture;

    @Resource
    private TencentCosConfig tencentCosConfig;

    @Resource
    @Qualifier(TencentCosConfig.COS_IMAGE)
    private COSClient cosClientPicture;

    @Resource
    private CategoryService categoryService;

    /**
     * 1、查询归档文章
     *
     * @return 归档文章列表
     */
    @Override
    public PageResult<ArchiveDTO> listArchives() {
        Page<Article> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        // 获取分页数据
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.select("article_id","article_title","create_time");
        wrapper.eq("is_delete",FALSE);
        wrapper.eq("status",PUBLIC.getStatus());
        wrapper.orderByDesc("create_time");
        wrapper.orderByDesc("article_id");
        Page<Article> articlePage = articleMapper.selectPage(page,wrapper);
        System.out.println(articlePage.getRecords().toString());
        // 这一步相当于将articlePage.getRecords()获得到的列表，提取出我们需要的ArchiveDTO的信息，其余丢弃
        List<ArchiveDTO> archiveDTOList = BeanCopyUtils.copyList(articlePage.getRecords(), ArchiveDTO.class);
        return new PageResult<>(archiveDTOList, (int) articlePage.getTotal());
    }

    /**
     * 2、查询所有文章
     *
     * @return 文章列表
     */
    @Override
    public List<ArticleHomeDTO> listArticles(Long current) {
        // 获取到除了标签列表外的所有需要属性
        List<ArticleHomeDTO> articleHomeDTOList = articleMapper.getArticleList((current-1)*10,PageUtils.getSize());
        // 将对应对articleId与List<TagDTO>对应起来
        for(int i=0; i<articleHomeDTOList.size(); i++){
            List<Tag> tagList = tagMapper.getTagListById(articleHomeDTOList.get(i).getArticleId());
            List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagList,TagDTO.class);
            articleHomeDTOList.get(i).setTagDTOList(tagDTOList);
        }
        return articleHomeDTOList;
    }

    /**
     * 3、根据id查看文章
     *
     * @param articleId 文章id
     * @return {@link ArticleDTO} 文章信息
     */
    @Override
    public ArticleDTO getArticleById(Integer articleId) {
        // todo: 推荐文章和最新文章 -> 未使用异步CompletableFuture
        // 查询推荐文章 => 设计到多表查询需要手写SQL语句
        List<ArticleRecommendDTO> recommendArticleList = articleMapper.listRecommendArticles(articleId);
        //查询最新文章
        QueryWrapper<Article> queryWrapperNew = new QueryWrapper<>();
        queryWrapperNew.orderByAsc("create_time");
        queryWrapperNew.last("limit 5");
        List<Article> newsArticleList1 = articleMapper.selectList(queryWrapperNew);
        List<ArticleRecommendDTO> newsArticleList = BeanCopyUtils.copyList(newsArticleList1,ArticleRecommendDTO.class);
        System.out.println(newsArticleList.toString());
        // 通过articleId查找文章信息
        ArticleDTO articleDTO = articleMapper.getArticleById(articleId);
        updateArticleViewsCount(articleId);
        //查询上一篇文章
        QueryWrapper<Article> queryWrapperLast = new QueryWrapper<>();
        queryWrapperLast.select("article_id","article_title","article_cover");
        queryWrapperLast.eq("is_delete",FALSE);
        queryWrapperLast.eq("status",PUBLIC.getStatus());
        queryWrapperLast.lt("article_id",articleId);
        queryWrapperLast.orderByDesc("article_id");
        queryWrapperLast.last("limit 1");
        Article lastArticle = articleMapper.selectOne(queryWrapperLast);
        articleDTO.setLastArticle(BeanCopyUtils.copyObject(lastArticle, ArticlePaginationDTO.class));
        //查询下一篇文章
        QueryWrapper<Article> queryWrapperNext = new QueryWrapper<>();
        queryWrapperNext.select("article_id","article_title","article_cover");
        queryWrapperNext.eq("is_delete",FALSE);
        queryWrapperNext.eq("status",PUBLIC.getStatus());
        queryWrapperNext.gt("article_id",articleId);
        queryWrapperNext.orderByDesc("article_id");
        queryWrapperNext.last("limit 1");
        Article nextArticle = articleMapper.selectOne(queryWrapperNext);
        articleDTO.setNextArticle(BeanCopyUtils.copyObject(nextArticle, ArticlePaginationDTO.class));
        // todo：封装点赞量
        Double score = redisService.zScore(ARTICLE_VIEWS_COUNT, articleId);
        if (Objects.nonNull(score)) {
            articleDTO.setViewsCount(score.intValue());
        }
        //封装文章信息
        try{
            articleDTO.setRecommendArticleList(recommendArticleList);
            articleDTO.setNewestArticleList(newsArticleList);
        }catch (Exception e){
            log.error(StrUtil.format("堆栈信息:{}", ExceptionUtil.stacktraceToString(e)));
        }
        return articleDTO;
    }


    /**
     * 更新文章浏览量
     *
     * @param articleId 文章id
     */
    public void updateArticleViewsCount(Integer articleId) {
        // 判断是否第一次访问，增加浏览量
        Set<Integer> articleSet = CommonUtils.castSet(Optional.ofNullable(session.getAttribute(ARTICLE_SET)).orElseGet(HashSet::new), Integer.class);
        if (!articleSet.contains(articleId)) {
            articleSet.add(articleId);
            session.setAttribute(ARTICLE_SET, articleSet);
            // 浏览量+1
            redisService.zIncr(ARTICLE_VIEWS_COUNT, articleId, 1D);
        }
    }


    /**
     * 4、根据类别条件查询文章列表
     *
     * @param condition 文章列表
     * @return {@link ArticlePreviewListDTO} 文章信息
     */
    @Override
    public ArticlePreviewListDTO listArticlesByCondition(ConditionVO condition) {
        // 查询文章
        // 对查询过程中使用对current进行调整，设置为传值的current
        Long current = condition.getLimitCurrent();
        System.out.println("current wei :" + current);
        List<ArticlePreviewDTO> articlePreviewDTOList = articleMapper.listArticlesByCondition((current-1)*10, 9L,condition);
        System.out.println("输出查找长度" + articlePreviewDTOList.size());
        // 将对应对articleId与List<TagDTO>对应起来
        for(int i=0; i<articlePreviewDTOList.size(); i++){
            List<Tag> tagList = tagMapper.getTagListById(articlePreviewDTOList.get(i).getArticleId());
            List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagList,TagDTO.class);
            articlePreviewDTOList.get(i).setTagDTOList(tagDTOList);
        }
        // 搜索条件对应名（标签或分类名）
        // todo 1
        String name;
        if(Objects.nonNull(condition.getCategoryId())){
            QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("category_name");
            queryWrapper.eq("category_id",condition.getCategoryId());
            name = categoryMapper.selectOne(queryWrapper).getCategoryName();
//            name = categoryMapper.selectOne(new LambdaQueryWrapper<Category>().select(Category::getCategoryName)
//                    .eq(Category::getCategoryId,condition.getCategoryId())).getCategoryName();
        }else{
            QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("tag_name");
            queryWrapper.eq("tag_id",condition.getTagId());
            name = tagMapper.selectOne(queryWrapper).getTagName();
//            name = tagMapper.selectOne(new LambdaQueryWrapper<Tag>().select(Tag::getTagName)
//                    .eq(Tag::getTagId,condition.getTagId())).getTagName();
        }
        return ArticlePreviewListDTO.builder().articlePreviewDTOList(articlePreviewDTOList).name(name).build();
    }


    /**
     * 5、搜索文章
     *
     * @param condition 条件
     * @return {@link Result <ArticleSearchDTO>} 文章列表
     */
    @Override
    public List<ArticleSearchDTO> listArticlesBySearch(ConditionVO condition) {
        // todo：搜索策略应该要用elasticsearch，但是此处用模糊查询代替
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if(condition.getKeywords() != null){
            queryWrapper.like("article_title",condition.getKeywords());
        }
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        // 复制属性
        List<ArticleSearchDTO> articleSearchDTOList = BeanCopyUtils.copyList(articleList,ArticleSearchDTO.class);
        return articleSearchDTOList;
    }

    /**
     * 6、点赞文章
     *
     * @param articleId 文章id
     * @return {@link Result<>}
     */
    @Override
    public void saveArticleLike(Integer articleId) {
        // todo：涉及到redisService

        // 通过articleId查找文章
        // 将点赞的用户与文章绑定 -> 属于点赞状态
    }

    /**
     * 7、添加或修改文章
     *
     * @param articleVO 文章信息
     * @return {@link Result<>}
     * */
    @Override
    public void saveOrUpdateArticle(ArticleVO articleVO) {
        // 查询博客配置信息 => 主要是为了获取文章的默认封面
        CompletableFuture<WebsiteConfigVO> webConfig = CompletableFuture.supplyAsync(()->blogInfoService.getWebsiteConfig());
        System.out.println("输出原有的categoryId" + articleVO.getCategoryId());
        // 保存文章分类 -> 根据前台信息传回来的信息保存分类信息
        // todo: 分类修改或保存
        Category category = saveArticleCategory(articleVO);
        System.out.println("输出后来的的categoryId：" + category.getCategoryId());
        // 保存或修改文章 -> 相当于前台传回来的articleVO中跟Article有关的信息都赋值给article
        Article article = BeanCopyUtils.copyObject(articleVO,Article.class);
        // 通过保存文章分类 -> 得到分类信息 -> 更新article的categoryId
        if(Objects.nonNull(category)){
            article.setCategoryId(category.getCategoryId());
        }
        // 设置文章的默认封面
        if(StrUtil.isBlank(article.getArticleCover())){
            try{
                article.setArticleCover(webConfig.get().getArticleCover());
            }catch (Exception e){
                throw new BizException("设定默认文章封面失败");
            }
        }
//        // 保存文章用户信息 -> 根据articleId是否为0
        article.setUserId(1);
        // 保存文章信息
        if(articleVO.getArticleId() != null){
//            articleMapper.updateById(article);
            article.setUpdateTime(new Date());
            articleMapper.update(article,new LambdaQueryWrapper<Article>().eq(Article::getArticleId,article.getArticleId()));
        }else{
            article.setCreateTime(new Date());
            articleMapper.insert(article);
        }
        // this.saveOrUpdate(article);
        // 保存文章标签 -> 此时的articleId，如果是新增则为自动生成的id、否则为传过来的原有的id
        saveArticleTag(articleVO,article);
        System.out.println("判断articleId 为：" + article.getArticleId());
    }


    /**
     * 7.1、保存文章分类
     *
     * @param articleVO 文章信息
     * @return {@link Category} 文章分类
     */
    private Category saveArticleCategory(ArticleVO articleVO) {
        // 查询是否已经存在
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_name",articleVO.getCategoryName());
        Category category = categoryMapper.selectOne(queryWrapper);
        // 判断修改为旧类别
        if(!Objects.isNull(category)){
            // 判断是否修改
            String currentName = categoryMapper.selectOne(new LambdaQueryWrapper<Category>()
                    .eq(Category::getCategoryId,articleVO.getCategoryId())).getCategoryName();
            System.out.println("currentname：" + currentName + " ， 要修改的name： " + articleVO.getCategoryName());
            if(currentName.equals(articleVO.getCategoryName())){
                System.out.println("类别没有进行i修改");
                return category;
            }
            // 修改之后为旧的类别
            // 判断当前在Article中categoryId对应的文章个数
            Integer count = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                    .eq(Article::getCategoryId,articleVO.getCategoryId()));
            if(count <= 1){
                // 只有一篇 -> 删除旧的分类信息
                categoryMapper.delete(new LambdaQueryWrapper<Category>()
                        .eq(Category::getCategoryId,articleVO.getCategoryId()));
            }
            return category;
        }else{
            category = Category.builder()
                    .categoryName(articleVO.getCategoryName())
                    .createTime(new Date())
                    .build(); // 通过建造者模式进行数据的赋值
            categoryMapper.insert(category);// 新增分类信息
            return category;
        }}

    /**
     * 保存文章标签
     *
     * @param articleVO 文章信息
     */
    private void saveArticleTag(ArticleVO articleVO, Article article) {
        // 更新Tag之后的文章类
        Article newArticle = BeanCopyUtils.copyObject(article,Article.class);
        // 编辑文章则删除文章所有标签 -> 防止过度存取
        if (Objects.nonNull(articleVO.getArticleId())) {
            // 通过articleId得到articleTagList（主要是要tagId属性）
            List<ArticleTag> articleTagList = articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>()
                    .eq(ArticleTag::getArticleId,article.getArticleId()));
            for(int i = 0; i < articleTagList.size(); i++){
//                // 判断tagId对应的文章个数
//                Integer count = articleTagMapper.selectCount(new LambdaQueryWrapper<ArticleTag>()
//                        .eq(ArticleTag::getTagId,articleTagList.get(i).getTagId()));
                // 删除articleId和tagId在ArticleTag类中的记录
//                articleTagMapper.deleteById(articleTagList.get(i).getArticleTagId());
                articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>()
                        .eq(ArticleTag::getTagId,articleTagList.get(i).getTagId())
                        .eq(ArticleTag::getArticleId,article.getArticleId()));
                // 在ArticleTag表中判断当前tag标签对应的文章数量，如果超过1，则不删除，否则删除
                Integer tagArticleCount = articleTagMapper.selectCount(new LambdaQueryWrapper<ArticleTag>()
                        .eq(ArticleTag::getTagId, articleTagList.get(i).getTagId()));
                if(tagArticleCount < 1){
                    tagMapper.deleteById(articleTagList.get(i).getTagId());
                }
            }

        }
        // 添加文章标签
        List<String> tagNameList = articleVO.getTagNameList();
        // 对tagNameList进去去重操作
        Integer tagId = 0;

        for(int i = 0; i < tagNameList.size(); i++){
            // 判断当前要插入的标签是否存在
            Tag tag = tagMapper.selectOne(new LambdaQueryWrapper<Tag>()
                    .eq(Tag::getTagName,tagNameList.get(i)));
            if(Objects.isNull(tag)){
                Tag tag1 = new Tag();
                // 如果tag为null，则标签不存在 -> 新建tag
                System.out.println("测试为什么为null" + tagNameList.get(i));
                tag1.setTagName(tagNameList.get(i));
                tag1.setCreateTime(new Date());
                tagMapper.insert(tag1);
                // 赋值tagId
                tagId = tagMapper.selectOne(new LambdaQueryWrapper<Tag>()
                        .eq(Tag::getTagName,tagNameList.get(i))).getTagId();
            }else{
                tagId = tag.getTagId();
            }
            // 通过articleId和tagId，保存article-tag表的记录
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(article.getArticleId());
            articleTag.setTagId(tagId);
            articleTagMapper.insert(articleTag);
        }

    }

    /**
     * 8、后台查看文章列表
     *
     * @param conditionVO 查询条件
     * @return {@Link Result<PageResult<ArticleBackDTO>>}
     * */
    @Override
    public PageResult<ArticleBackDTO> listBackArticles(ConditionVO conditionVO) {
        // 查询文章总数
        Integer count = articleMapper.selectCount(new LambdaQueryWrapper<Article>().eq(Article::getIsDelete,FALSE));
        if(count == 0){ // 没有文章
            return new PageResult<>();
        }
        // 查询后台文章
        List<ArticleBackDTO> articleBackDTOList = articleMapper.listBackArticle((conditionVO.getLimitCurrent()-1)*10,conditionVO.getSize(),conditionVO);
        System.out.println("后台文章" + articleBackDTOList);
        // 通过文章查找对应的文章标签
        for(int i = 0; i < articleBackDTOList.size(); i++){
            // 存储每个文章对应的标签
            List<TagDTO> tagDTOList = new ArrayList<>();
            // 获得文章id -> 从article_tag中找到对应的tag_id
            List<ArticleTag> articleTagList = articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>()
                    .eq(ArticleTag::getArticleId,articleBackDTOList.get(i).getArticleId()));
            for(int j = 0; j < articleTagList.size(); j++){
                tagDTOList.add(BeanCopyUtils.copyObject(tagMapper.selectOne(new LambdaQueryWrapper<Tag>().eq(Tag::getTagId,articleTagList.get(j).getTagId())),TagDTO.class));
            }
            articleBackDTOList.get(i).setTagDTOList(tagDTOList);
        }
        // 查询文章浏览量
        Map<Object, Double> viewsCountMap = redisService.zAllScore(ARTICLE_VIEWS_COUNT);
        // 封装浏览量
        articleBackDTOList.forEach(item -> {
            Double viewsCount = viewsCountMap.get(item.getArticleId());
            if (Objects.nonNull(viewsCount)) {
                item.setViewsCount(viewsCount.intValue());
            }
        });
        return new PageResult<>(articleBackDTOList, count);
    }
    /**
     * 9、修改文章是否置顶
     *
     * @param articleId,isTop 修改置顶
     * @return {@link Result<?>}
     * */
    @Override
    public void updateArticleTop(Integer articleId, Integer isTop) {
        Article article = articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                .eq(Article::getArticleId, articleId));
        article.setIsTop(isTop);
        articleMapper.updateById(article);
    }

    @Override
    public ArticleVO findBackArticleById(Integer articleId) {
        // 查找文章信息
        Article article = articleMapper.selectById(articleId);
        // 查询文章分类 -> 获取类别名称
        Category category = categoryMapper.selectById(article.getCategoryId());
        String categoryName = category.getCategoryName();
        // 查找文章标签
        List<Tag> tagList = tagMapper.getTagListById(articleId);
        List<String> tagNameList = new ArrayList<>();
        for(int i = 0; i < tagList.size(); i++){
            tagNameList.add(tagList.get(i).getTagName());
        }
        // 封装数据
        ArticleVO articleVO = BeanCopyUtils.copyObject(article,ArticleVO.class);
        articleVO.setCategoryName(categoryName);
        articleVO.setTagNameList(tagNameList);
        return articleVO;
    }

    // 添加文件-图片
    @Override
    public String getUploadFileTencentCosUrl(MultipartFile multipartFile) {
        File localFile = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split("\\.");
            localFile=File.createTempFile(filename[0], filename[1]);
            multipartFile.transferTo(localFile);
            localFile.deleteOnExit();
        } catch (IOException e) {
            throw new BizException("文件上传失败");
        }

        if(localFile == null){
            throw new BizException("本地文件为空");
        }

        String key = TencentCosConfig.COS_IMAGE + "/" + new Date().getTime() + ".png";

        PutObjectRequest putObjectRequest =
                new PutObjectRequest(tencentCosPropertiesPicture.getBucketName(), key, localFile);
        //设置存储类型 默认标准型
        putObjectRequest.setStorageClass(StorageClass.Standard);

        COSClient cosClient = tencentCosConfig.getCoSClient4Picture();

        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            //putObjectResult 会返回etag
            String etag = putObjectResult.getETag();
        } catch (Exception e) {
            throw new BizException("文件上传失败");
        }
        cosClient.shutdown();
        String url = tencentCosPropertiesPicture.getBaseUrl()+ "/" + key;
        return url;
    }

    @Override
    public void updateArticleDelete(Integer articleId) {
        // 删除文章信息 -> 删除文章信息前，先获得categoryId
        Integer categoryId = articleMapper.selectOne(new LambdaQueryWrapper<Article>()
                .eq(Article::getArticleId,articleId)).getCategoryId();
        // 删除文章分类信息： 需要看当前分类是否有其他文章
        Integer categoryCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getCategoryId,categoryId));
        System.out.println("输出当前的目录下的文章个数： " + categoryCount);
        if(categoryCount == 1){ //如果只有一个，意味着该目录只对应当前文章，故而进行删除操作
            categoryMapper.deleteById(categoryId);
        }
        // 删除文章
        articleMapper.deleteById(articleId);
        // 删除文章标签信息： 需要查看当前分类是否有其他文章
        // 查找当前articleId对应的tagIdList，再删除
        List<ArticleTag> articleTagList = articleTagMapper.selectList(new LambdaQueryWrapper<ArticleTag>()
                .eq(ArticleTag::getArticleId,articleId));
        for(int i = 0; i < articleTagList.size(); i++){ // 赋值为tagIdList
            // 删除articleId在ArticleTag类中的记录
            articleTagMapper.delete(new LambdaQueryWrapper<ArticleTag>()
                    .eq(ArticleTag::getArticleId,articleId)
                    .eq(ArticleTag::getTagId,articleTagList.get(i).getTagId()));
            // 在ArticleTag表中判断当前tag标签对应的文章数量，如果超过1，则不删除，否则删除
            Integer tagArticleCount = articleTagMapper.selectCount(new LambdaQueryWrapper<ArticleTag>()
                    .eq(ArticleTag::getTagId, articleTagList.get(i).getTagId()));
            if(tagArticleCount < 1){
                tagMapper.deleteById(articleTagList.get(i).getTagId());
            }
        }
//        // 删除文章信息 -> 删除文章信息前，先获得categoryId
//        Integer categoryId = articleMapper.selectOne(new LambdaQueryWrapper<Article>()
//                .eq(Article::getArticleId,articleId)).getCategoryId();
//        System.out.println("输出categoryId ： " + categoryId);
//        // 删除文章分类信息： 需要看当前分类是否有其他文章
//        Integer categoryCount = articleMapper.selectCount(new LambdaQueryWrapper<Article>()
//                .eq(Article::getCategoryId,categoryId));
//        System.out.println("输出当前的目录下的文章个数： " + categoryCount);
//        if(categoryCount == 1){ //如果只有一个，意味着该目录只对应当前文章，故而进行删除操作
//            System.out.println("失败");
//        }

    }

    /**
     * 14、保存草稿
     *
     * @param articleVO 查询条件
     * */
    @Override
    public void saveDraft(ArticleVO articleVO) {
        // 保存基本的信息，其实就是不发布而已，和发布没有很大的区别
    }


}
