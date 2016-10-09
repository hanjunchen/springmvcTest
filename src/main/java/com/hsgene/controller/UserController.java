package com.hsgene.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hsgene.entity.User;
import com.hsgene.exception.UserException;
import javafx.application.Application;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by hjc on 2016/9/27.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    //  @RequestMapping映射的不仅仅是url，还包括参数(@RequestMapping还有params参数可以设置)、提交方式等信息的映射，所以即使两个url的相同，如果参数不同那么springMVC也会根据其他信息映射到对应的方法上

    private Map<String, User> map = Maps.newLinkedHashMap(); // 这里为了模拟往数据库里面存数据，正常不允许给Controller添加可变成员变量

    public UserController() {   // 由于springMVC的Controller是一个单例，所以list只会在Controller第一次初始化时添加元素
        map.put("1", new User("1", "呵呵", "111"));
        map.put("2", new User("2", "小明", "222"));
        map.put("3", new User("3", "小黑", "333"));
    }

    @RequestMapping(value = "list", method = RequestMethod.GET) // 获取全部列表用get方式，表单提交用post
    public String findList(Model model, Model model2) {   // 将list添加到Model返回，多个Model作为参数，springMVC最终会将所有Model和Map中的数据合并到一个Model中，如果重复插入了相同的key值，那么后插入的value会覆盖前者，遵循Map键唯一原则
        List<User> list = Lists.newArrayList();
        list.add(new User("1", "呵呵", "111"));
        list.add(new User("2", "小明", "222"));
        list.add(new User("3", "小黑", "333"));
        model.addAttribute("userList", list);
        return "userList";  // 返回一个逻辑视图名，如果是重定向："redirect:/user/list"和转发："forward:/user/list"则不是作为逻辑视图名，而是返回一个新的请求；注意三者的区别
    }

    @RequestMapping(value = "list2", method = RequestMethod.GET)
    //  springMVC基于Servlet，struts基于过滤器----Servlet的API都可以作为参数根据情况添加，例如session，application等
    //  Model的作用域和Request一样
    public String findList2(HttpServletRequest request, HttpServletResponse response, HttpSession session, ServletContext servletContext, Application application) {   // 将list添加到request中，springMVC中使用Servlet的API只需要将其作为参数传入方法即可，非常方便
        List<User> list = Lists.newArrayList();
        list.add(new User("1", "呵呵", "111"));
        list.add(new User("2", "小明", "222"));
        list.add(new User("3", "小黑", "333"));
        request.setAttribute("userList", list);
        return "userList";
    }

    @RequestMapping(value = "list3", method = RequestMethod.GET)
    public String findList3(Model model) {  // 将map添加到Model返回
        Map<String, User> map = Maps.newLinkedHashMap();    // LinkedHashMap可以保存插入的顺序
        map.put("1", new User("1", "呵呵", "111"));
        map.put("2", new User("2", "小明", "222"));
        map.put("3", new User("3", "小黑", "333"));
        model.addAttribute("userMap", map);
        return "userList";
    }

    @RequestMapping(value = "list4", method = RequestMethod.GET)
    public String findList4(Model model) {
        model.addAttribute("userMap", map);
        return "userList";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    // @ModelAttribute作用就相当于model.addAttribute(new User())，请求进来没有参数，但是仍会实例化一个空的User对象，通过这个注解将其添加到Model中，一般用在不需要改变对象或者需要对象初始状态的情况
    public String add(@ModelAttribute("user") User user) { // 这个方法是为了给添加页面一个空的User对象模型，从而方便表单提交时的自动封装
        return "adduser";
    }

    @RequestMapping(value = "addSave", method = RequestMethod.POST)  // 表单提交使用post方式
    public String addSave(@Valid User user, BindingResult bindingResult) {//  注意@Valid(也可以使用@Validated)和BindingResult对象必须紧跟在一起
        if (bindingResult.hasErrors()) {
            return "adduser";
        }
        map.put(user.getId(), user);
        return "redirect:/user/list4";  // 重定向：客户端重新向Controller中的一个方法发送请求；forward是转发，类似于逻辑视图名方式，但是其本质实现有区别
    }

    //  REST风格仅仅是参数在url上的一种显示，本质上不会影响实际的url，比如：/user/{id}/view的真是url就是/user/view，夹杂在其中的id参数只是这种显示风格而已
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)  //  a标签都是GET请求
    public String view(@PathVariable String id, Model model) {   // 该注解用于获取REST风格的url中的参数
        model.addAttribute("user", map.get(id));
        return "view";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String update(@PathVariable String id, Model model) {
        model.addAttribute(map.get(id));
        return "update";
    }

    @RequestMapping(value = "/{id}/updateSave", method = RequestMethod.POST)
    public String updateSave(@PathVariable String id, @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update";    //  当验证不通过时，填错的user对象也会随着BindingResult和错误信息一起被返回
        }
        map.put(id, user);  // 直接覆盖原有的
        return "redirect:/user/list4";
    }

    @RequestMapping(value = "/{id}/delete",method = RequestMethod.GET)
    public String delete(@PathVariable String id){
        map.remove(id);
        return "redirect:/user/list4";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(User user,HttpSession session){
        // 遍历map四种方法
        // 1、map.values()
        boolean flag = false;
        for (User user1 : map.values()) {
            if(user1.getName().equals(user.getName())&&user1.getPassword().equals(user.getPassword())){
                session.setAttribute("currentUser",user);
                flag = true;
                break;
            }
        }
        // 2、map.keySet()
        for (String s : map.keySet()) {
            System.out.println("name:" + map.get(s).getName() + "---password:" + map.get(s).getPassword());
        }
        // 3、map.entrySet().iterator() 此种方法效率最高,Entry就是原来的map，然后外面又套了一层集合（entrySet）
        Iterator<Map.Entry<String,User>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,User> entry = iterator.next();
            System.out.println("name:" + entry.getKey() + "---password:" + entry.getValue());
        }
        // 4、直接遍历map.entrySet()
        if(!flag){
            throw new UserException("用户名密码错误");
        }
        return "redirect:/user/list4";
    }

    @ExceptionHandler(value = {UserException.class})    //  用于处理局部异常，一般局限于当前类，value是一个数组，指定处理的异常类型，该类中所有抛出的指定类型的异常都被该方法拦截
    public String HandlerException(UserException e,Model model){
        model.addAttribute("e",e);
        return "error";
    }
}
