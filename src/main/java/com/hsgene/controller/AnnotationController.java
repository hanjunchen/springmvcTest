package com.hsgene.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by hjc on 2016/9/25.
 */
@Controller
//@RequestMapping(value = "/annotationController") // 加上/表示根目录
public class AnnotationController {// 注解方式不需要继承任何接口和类

    @RequestMapping(value = "/index")   // 加上/表示根目录，是绝对路径，不加/表示相对路径，相对Controller的路径
    public String index(@RequestParam(value = "id", required = false) String id) {  // @RequestParam注解指定传入的参数，默认required参数为true，所以默认必须传参
        System.out.println("测试注解方式配置控制器！--- " + id);
        return "springmvc";
    }

    @RequestMapping(value = "/test1")
    public String test1(String name,Model model) {    // @RequestParam注解默认是可以省略的，并且默认的required值为false，即不必需传参
        System.out.println("hello --- " + name);
        name = "哈哈";
        model.addAttribute("name",name);    // spring自身的Model数据模型，Map结构传递数据，会随着逻辑视图名被视图解析器解析，可以使用EL表达式输出，由此该跳转是页面转发
        model.asMap().values().forEach(x-> System.out.println(x));
        return "springmvc";
    }
}
