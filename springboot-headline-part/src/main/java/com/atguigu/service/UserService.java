package com.atguigu.service;

import com.atguigu.pojo.User;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author HONOR
* @description 针对表【news_user】的数据库操作Service
* @createDate 2024-01-07 20:20:19
*/
public interface UserService extends IService<User> {

    /*实现登录的逻辑*/
    Result login(User user);

    /*根据用户token来获取用户信息*/
    Result getUserInfo(String token);

    Result checkUserName(String username);

    Result regist(User user);
}
