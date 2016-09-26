package com.hsgene.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by hjc on 2016/9/25.
 */
@Controller
//@RequestMapping(value = "/annotationController") // 加上/表示根目录
public class AnnotationController {// 注解方式不需要继承任何接口和抽象类

    @RequestMapping(value = "index") // 加上/表示根目录，是绝对路径，不加/表示相对路径，相对Controller的路径
    public String index(){
        System.out.println("测试注解方式配置控制器！");
        return "springmvc02";
    }
}
