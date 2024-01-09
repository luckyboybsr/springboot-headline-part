package com.atguigu.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.MD5Util;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.pojo.User;
import com.atguigu.service.UserService;
import com.atguigu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author HONOR
* @description 针对表【news_user】的数据库操作Service实现
* @createDate 2024-01-07 20:20:19
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtHelper jwtHelper;
    /*
    * 登录业务：
    *   1、根据账号，查询用户对象 --loginUser
    *   2、如果用户对象为null 查询失败 账号错误！ 501
    *   3、对比，密码 失败 返回503 的错误
    *   4、根据用户id生成一个token,token ->result 返回
    *
    *
    *
    *
    * */
    @Override
    public Result login(User user) {
        //根据账号查数据
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());
        User loginUser = userMapper.selectOne(lambdaQueryWrapper);
        if(loginUser==null){
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        //判断密码
        if (!StringUtils.isEmpty(user.getUserPwd())
                && loginUser.getUserPwd().equals(MD5Util.encrypt(user.getUserPwd()))){
            //登录成功
            //根据用户id生成 token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            //将token封装到result返回
            Map data = new HashMap();
            data.put("token", token);
            return Result.ok(data);
        }
        //密码错误
        return Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
    }
/** 根据用户的token来获取用户信息
 *  1、判断token是否在有效期
 *  2、根据token解析userId
 *  3、根据用户id查询数据
 *  4、要求去掉密码，避免泄露，最后封装到result结果返回即可
 *
 */
    @Override
    public Result getUserInfo(String token) {
        //判断是否过期，如果返回为true，则表示已经过期
        boolean expiration = jwtHelper.isExpiration(token);
        if(expiration){
            //失效 则当做未登录
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        int userId = jwtHelper.getUserId(token).intValue();
        User user = userMapper.selectById(userId);
        user.setUserPwd("");
        Map data = new HashMap();
        data.put("loginUser", user);
        return Result.ok(data);
    }
    /** 检查账号是否可用
     *      1、根据账号进行count进行查询
     *      2、count==0 可用
     *      3、count>0 不可用
     *
     */

    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        long count = userMapper.selectCount(queryWrapper);

        if (count==0) {
            return Result.ok(null);
        }
        return Result.build(null, ResultCodeEnum.USERNAME_USED);

    }
    /**
     *  注册逻辑
     *     1、依然检查账号是否已经被注册
     *     2、密码加密处理
     *     3、账号数据保存
     *     4、返回结果
     */

    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        Long count = userMapper.selectCount(queryWrapper);
        if(count>0){
            return Result.build(null, ResultCodeEnum.USERNAME_USED);

        }
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));//对密码进行加密，然后把加过密的密码保存到数据库中
        userMapper.insert(user);
        return Result.ok(null);
    }
}




