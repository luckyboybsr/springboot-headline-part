package com.atguigu.controller;

import com.atguigu.pojo.vo.PortalVo;
import com.atguigu.service.HeadlineService;
import com.atguigu.service.TypeService;
import com.atguigu.utils.JwtHelper;
import com.atguigu.utils.Result;
import com.atguigu.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: springboot-headline-part
 * @BelongsPackage com.atguigu.controller
 * @Author: 闭仕荣
 * @Date: 2024/1/8 14:10
 * @description: 首页的控制层
 */
@RequestMapping("portal")
@CrossOrigin
@RestController
public class PortalController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private HeadlineService headlineService;

    @GetMapping("findAllTypes")
    public Result findAllTypes(){
       Result result= typeService.findAllTypes();
        return result;
    }

    @PostMapping("findNewsPage")
    public Result findNewsPage(@RequestBody PortalVo portalVo){
       Result result= headlineService.findNewsPage(portalVo);
       return result;
    }

    @PostMapping("showHeadlineDetail")
    public Result showHeadlineDetail(Integer hid){
        Result result = headlineService.showHeadlineDetail(hid);
        return result;
    }


}