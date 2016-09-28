package com.hsgene.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by hjc on 2016/9/25.
 */
@Controller
//@RequestMapping(value = "/annotationController") // 加上/表示根目录
public class AnnotationController { // 注解方式不需要继承任何接口和类

    private String userName;    // struts2采用这种方式传参，可以通过注解@Scope(“prototype”)设置action为多例，而springMVC的Controller采用的是单例，所以在性能上springMVC比struts快的多
    // Controller支持多线程，springMVC控制器中应尽量避免使用成员变量，否则变量共享后将数据不安全，方法上如果不加synchronized方法是不同步的，即多个用户访问互不影响

    @RequestMapping(value = "/index")   // 加上/表示根目录，是绝对路径，不加/表示相对路径，相对Controller的路径
    public String index(@RequestParam(value = "id", required = false) String id) {  // @RequestParam注解指定传入的参数，默认required参数为true，所以默认必须传参
        System.out.println("测试注解方式配置控制器！--- " + id);
        return "springmvc";
    }

    @RequestMapping(value = "/test1")
    public String test1(String name, Model model) {    // @RequestParam注解默认是可以省略的，并且默认的required值为false，即不必需传参
        System.out.println("test1 --- " + name);
        model.addAttribute("name", name);    // spring自身的Model数据模型，Map结构传递数据，会随着逻辑视图名被视图解析器解析，可以使用EL表达式输出
        model.addAttribute(name);   // 这种写法不能往Model中加入空属性
        model.asMap().values().forEach(x -> System.out.println(x));
        return "springmvc";
    }

    @RequestMapping(value = "/test2")
    public ModelAndView test1(String name) {    // 这种方式就相当于上面的Model方式，将数据模型和逻辑视图名显示封装在ModelAndView中并返回，Model方式是隐式创建Model实例后在逻辑视图名中携带数据模型并返回
        System.out.println("test2 --- " + name);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("springmvc");
        modelAndView.addObject("name", name);
        modelAndView.addObject(name);   // 这种不指定key值，springmvc默认将key值设为数据类型的小写，即相当于modelAndView.addObject("string",name);
        // modelAndView.addObject(new User()); 常用于将对象直接加入模型中，默认的key值是类小写-->modelAndView.addObject("user",new User());
        // key、value设值规则和Model一样，Model设值也可以不指定key，具有同样的默认值
        return modelAndView;
    }

    @RequestMapping(value = "/test3")
    public String test1(String name, Map<String, Object> map) { // 将Model替换成Map同样可行，因为Model本身就是一个Map，其实现类继承自Map，一般不用这种方式
        System.out.println("test3 --- " + name);
        map.put("name", name);
        return "springmvc";
    }
}
