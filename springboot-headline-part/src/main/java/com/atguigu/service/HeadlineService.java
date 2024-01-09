package com.atguigu.service;

import com.atguigu.pojo.Headline;
import com.atguigu.pojo.vo.PortalVo;
import com.atguigu.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author HONOR
* @description 针对表【news_headline】的数据库操作Service
* @createDate 2024-01-07 20:20:19
*/
public interface HeadlineService extends IService<Headline> {

    Result findNewsPage(PortalVo portalVo);

    Result showHeadlineDetail(Integer hid);


    /*
    *   发布头条方法
    *   1、补全数据
    * */
    Result publish(Headline headline, String token);

    Result updateData(Headline headline);
}
