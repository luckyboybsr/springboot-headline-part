package com.atguigu.pojo.vo;

import lombok.Data;

/**
 * @program: springboot-headline-part
 * @BelongsPackage com.atguigu.pojo.vo
 * @Author: 闭仕荣
 * @Date: 2024/1/8 14:26
 * @description:
 */
@Data
public class PortalVo {
    private String keyWords;
    private int type=0;//设置默认值
    private int pageNum=1;
    private int pageSize=10;
}