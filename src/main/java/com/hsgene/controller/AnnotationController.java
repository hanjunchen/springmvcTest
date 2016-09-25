package com.hsgene.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hjc on 2016/9/25.
 */
@Controller("/annotationController") // 路径需要加上/
public class AnnotationController {// 注解方式不需要继承任何接口和抽象类

    @RequestMapping("index") // 路径需要加上/
    public String index(){
        System.out.println("测试注解方式配置控制器！");
        return "springmvc02";
    }
}
