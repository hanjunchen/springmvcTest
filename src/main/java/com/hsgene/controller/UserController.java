package com.hsgene.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hsgene.entity.User;
import javafx.application.Application;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by hjc on 2016/9/27.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    //  @RequestMapping映射的不仅仅是url，还包括参数(@RequestMapping还有params参数可以设置)、提交方式等信息的映射，所以即使两个url的相同，如果参数不同那么springMVC也会根据其他信息映射到对应的方法上

    private List<User> list = Lists.newArrayList(); // 这里为了模拟往数据库里面存数据，正常不允许给Controller添加可变成员变量

    public UserController() {   // 由于springMVC的Controller是一个单例，所以list只会在Controller第一次初始化时添加元素
        list.add(new User("1", "呵呵", 18));
        list.add(new User("2", "小明", 28));
        list.add(new User("3", "小黑", 22));
    }

    @RequestMapping(value = "list", method = RequestMethod.GET) // 获取全部列表用get方式，表单提交用post
    public String findList(Model model, Model model2) {   // 将list添加到Model返回，多个Model作为参数，springMVC最终会将所有Model和Map中的数据合并到一个Model中，如果重复插入了相同的key值，那么后插入的value会覆盖前者，遵循Map键唯一原则
        List<User> list = Lists.newArrayList();
        list.add(new User("1", "呵呵", 18));
        list.add(new User("2", "小明", 28));
        list.add(new User("3", "小黑", 22));
        model.addAttribute("userList", list);
        return "userList";  // 返回一个逻辑视图名，如果是重定向："redirect:/user/list"和转发："forward:/user/list"则不是作为逻辑视图名，而是返回一个新的请求；注意三者的区别
    }

    @RequestMapping(value = "list2", method = RequestMethod.GET)
    //  springMVC基于Servlet，struts基于过滤器----Servlet的API都可以作为参数根据情况添加，例如session，application等
    //  Model的作用域和Request一样
    public String findList2(HttpServletRequest request, HttpSession session, ServletContext servletContext, Application application) {   // 将list添加到request中，springMVC中使用Servlet的API只需要将其作为参数传入方法即可，非常方便
        List<User> list = Lists.newArrayList();
        list.add(new User("1", "呵呵", 18));
        list.add(new User("2", "小明", 28));
        list.add(new User("3", "小黑", 22));
        request.setAttribute("userList", list);
        return "userList";
    }

    @RequestMapping(value = "list3", method = RequestMethod.GET)
    public String findList3(Model model) {  // 将map添加到Model返回
        Map<String, User> map = Maps.newLinkedHashMap();    // LinkedHashMap可以保持插入的顺序
        map.put("1", new User("1", "呵呵", 18));
        map.put("2", new User("2", "小明", 28));
        map.put("3", new User("3", "小黑", 22));
        model.addAttribute("userMap", map);
        return "userList";
    }

    public String add(){
        return "";
    }
}
