package com.chenzhihui.blog.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.chenzhihui.blog.constant.CommonConst;
import com.chenzhihui.blog.dto.EmailDTO;
import com.chenzhihui.blog.enums.LoginTypeEnum;
import com.chenzhihui.blog.enums.RoleEnum;
import com.chenzhihui.blog.exception.BizException;
import com.chenzhihui.blog.mapper.UserRoleMapper;
import com.chenzhihui.blog.pojo.UserAuth;
import com.chenzhihui.blog.mapper.UserAuthMapper;
import com.chenzhihui.blog.pojo.UserInfo;
import com.chenzhihui.blog.pojo.UserRole;
import com.chenzhihui.blog.service.BlogInfoService;
import com.chenzhihui.blog.service.UserAuthService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenzhihui.blog.vo.Result;
import com.chenzhihui.blog.vo.UserVO;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import static com.chenzhihui.blog.constant.MQPrefixConst.EMAIL_EXCHANGE;
import static com.chenzhihui.blog.util.CommonUtils.checkEmail;
import static com.chenzhihui.blog.util.CommonUtils.getRandomCode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class UserAuthServiceImpl extends ServiceImpl<UserAuthMapper, UserAuth> implements UserAuthService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    //注入邮件发送
    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Resource
    private UserAuthMapper userAuthMapper;

    @Resource
    private BlogInfoService blogInfoService;

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 发送邮箱验证码
     *
     * @param username 用户名
     * @return {@link Result <>}
     */
    @Override
    public void sendCode(String username) {
        // 检测账号是否合法
        if(!checkEmail(username)){
            throw new BizException("请输入正确的邮箱");
        }
        // 生成六位数随机验证码发送
        String code = getRandomCode();
        // 发送验证码
        EmailDTO emailDTO = EmailDTO.builder()
                .email(username)
                .subject("验证码")
                .content("您的验证码为 " + code + "有效期为15分钟，请勿外传")
                .build();
        // todo：将验证码放入redis中未实现

        // todo：发送消息用rabbitmq
//        System.out.println("1111" + code);
//        rabbitTemplate.convertAndSend(EMAIL_EXCHANGE,"*",new Message(JSON.toJSONBytes(emailDTO),new MessageProperties()));
//        System.out.println("2222");

         // 发送消息
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("验证码");
        simpleMailMessage.setText("您的验证码为" + code + "有效期15分钟");
        simpleMailMessage.setTo(username);
        simpleMailMessage.setFrom("2425540101@qq.com");
        javaMailSender.send(simpleMailMessage);
    }


    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return {@link Result<>}
     */
    @Transactional(rollbackFor = Exception.class) // todo: 有什么效果
    @Override
    public void register(UserVO user) {
        // 检测账号是否合法
        if(checkUser(user)){
            throw new BizException("邮箱以被注册");
        }
        // 新增用户信息
        UserInfo userInfo = UserInfo.builder()
                .email(user.getUsername())
                .nickname(CommonConst.DEFAULT_NICKNAME + IdWorker.getId()) // 什么意思
                .avatar(blogInfoService.getWebsiteConfig().getUserAvatar())
                .build();
        // 绑定用户角色
        UserRole userRole = UserRole.builder()
                .userId(userInfo.getUserInfoId())
                .roleId(RoleEnum.USER.getRoleId())
                .build();
        userRoleMapper.insert(userRole);
        // 新增用户账号
        UserAuth userAuth = UserAuth.builder()
                .userInfoId(userInfo.getUserInfoId())
                .username(user.getUsername())
                .password(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt()))
                .loginType(LoginTypeEnum.EMAIL.getType())
                .build();
        userAuthMapper.insert(userAuth);
    }


    /**
     * 校验用户数据是否合法
     *
     * @param user 用户数据
     * @return 结果
     */
    private Boolean checkUser(UserVO user) {
        // todo: 还未判断redis里的值
//        if (!user.getCode().equals(redisService.get(USER_CODE_KEY + user.getUsername()))) {
//            throw new BizException("验证码错误！");
//        }
        //查询用户名是否存在
        UserAuth userAuth = userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .select(UserAuth::getUsername)
                .eq(UserAuth::getUsername, user.getUsername()));
        return Objects.nonNull(userAuth);
    }

    /**
     * 修改密码
     *
     * @param user 用户信息
     * @return {@link Result<>}
     */
    @Override
    public void updatePassword(UserVO user) {
        // 校验账号是否合法
        if(!checkUser(user)){
            throw  new BizException("账号未注册");
        }
        // 根据用户名修改密码
        userAuthMapper.update(new UserAuth(), new LambdaUpdateWrapper<UserAuth>()
                .set(UserAuth::getPassword, org.springframework.security.crypto.bcrypt.BCrypt.hashpw(user.getPassword(), org.springframework.security.crypto.bcrypt.BCrypt.gensalt()))
                .eq(UserAuth::getUsername, user.getUsername()));
    }

    /**
     * 判断是否登录成功
     *
     * @param userVO 用户信息
     * @return {@link Integer}
     */
    @Override
    public Integer check(UserVO userVO) {
        UserAuth userAuth =  userAuthMapper.selectOne(new LambdaQueryWrapper<UserAuth>()
                .eq(UserAuth::getUsername,userVO.getUsername())
                .eq(UserAuth::getPassword,userVO.getPassword()));
        if(Objects.isNull(userAuth)){
            return 0;
        }else{
            return 1;
        }
    }


}
