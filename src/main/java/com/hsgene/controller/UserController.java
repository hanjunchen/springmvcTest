package com.hsgene.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hsgene.entity.User;
import com.hsgene.exception.UserException;
import javafx.application.Application;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
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
    // 注意不能用于接收有效参数的请求，即请求的user中有参数就不能用这个注解
    // 当参数是对象时，并且参数名就是javaBean的小写，是可以省略这个注解的，实际不管参数是什么名称，只要是对象类型都会自动往Model中放一个javaBean小写的对象并返回
    // 所以实际应用中不推荐省略的写法
    public String add(@ModelAttribute("user") User user) { // 这个方法是为了给添加页面一个空的User对象模型，从而方便表单提交时的自动封装
        return "adduser";
    }

    @RequestMapping(value = "addSave", method = RequestMethod.POST)  // 表单提交使用post方式
    //  注意@Valid(也可以使用@Validated)和BindingResult对象必须紧跟在一起
    //  上传的文件保存在MultipartFile对象中
    //  MultipartFile类型的可以不指定@RequestParam注解，如果是多文件上传，MultipartFile[]则必须要指定@RequestParam，否则框架无法解析数组类型
    public String addSave(@Valid User user, BindingResult bindingResult, @RequestParam MultipartFile[] attaches, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "adduser";
        }
        map.put(user.getId(), user);
        if (attaches != null) {
            //  获取服务器上传文件的目录
            String filePath = request.getSession().getServletContext().getRealPath("/statics/upload");
            try {
                for (MultipartFile attach : attaches) {
                    if (!attach.isEmpty()) {
                        String fileName = attach.getOriginalFilename(); //  上传文件名
                        System.out.println(attach.getName());// 获取上传文件表单的name值
                        System.out.println(attach.getContentType());//  获取上传文件类型
                        //  创建文件，正常创建文件需要判断文件是否存在，而copyInputStreamToFile方法已经封装了判断的操作
                        //  File.separator分隔符兼容windows和linux
                        File file = new File(filePath + File.separator + fileName);
                        FileUtils.copyInputStreamToFile(attach.getInputStream(), file); //  通过文件的输入流将文件复制到file中
                        //  同名文件不会覆盖已存在文件
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 重定向：客户端重新向Controller中的一个方法发送请求；forward是转发，类似于逻辑视图名方式，但是其本质实现有区别
        // redirect：后面的是url请求（重新请求Controller中的一个方法），普通字符串是逻辑视图名（就是一个jsp页面）
        return "redirect:/user/list4";
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
            //  当验证不通过时，填错的user对象也会随着BindingResult和错误信息一起被返回，实际上是错误的user会被放进Model中，而Model已经被封装在BindingResult中
            return "update";
        }
        map.put(id, user);  // 直接覆盖原有的
        return "redirect:/user/list4";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable String id) {
        map.remove(id);
        return "redirect:/user/list4";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(User user, HttpSession session) {
        // 遍历map四种方法
        // 1、map.values()
        boolean flag = false;
        for (User user1 : map.values()) {
            if (user1.getName().equals(user.getName()) && user1.getPassword().equals(user.getPassword())) {
                session.setAttribute("currentUser", user);
                flag = true;
                break;
            }
        }
        // 2、map.keySet()
        for (String s : map.keySet()) {
            System.out.println("name:" + map.get(s).getName() + "---password:" + map.get(s).getPassword());
        }
        // 3、map.entrySet().iterator() Iterator方法是所有遍历中效率最高的,Entry就是原来的map，然后外面又套了一层集合（entrySet）
        Iterator<Map.Entry<String, User>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, User> entry = iterator.next();
            System.out.println("name:" + entry.getValue().getName() + "---password:" + entry.getValue().getPassword());
        }
        // 4、直接遍历map.entrySet()
        if (!flag) {
            throw new UserException("用户名或密码错误");
        }
        return "redirect:/user/list4";
    }

    //    @ExceptionHandler(value = {UserException.class})    //  用于处理局部异常，一般局限于当前类，value是一个数组，指定处理的异常类型，该类中所有抛出的指定类型的异常都被该方法拦截
    //    public String HandlerException(UserException e,HttpServletRequest request){ // 也可以用Model代替HttpServletRequest，他们的作用域是一样的
    //        request.setAttribute("e", e);
    //        return "../error";
    //    }

    @RequestMapping(value = "testModelAndMap")
    public String test(Model model, Model model2, Map map) {
        // Model就是一个Map，即一个hash表，hash<obj,obj> 数据结构中的hash表的key是一个对象，包含了value的名称、内存地址等信息，所以就是value的索引
        // 所以可以通过key找到value
        // 这里的3个参数实际上是同一个Map，不管操作哪一个model或者map实际操作的都是同一个Model，后加同名key会覆盖先加的
        // 相当于是同一个变量
        // 这个现象的原理在于springMVC映射url之前会经过多层处理：如HandlerAdapter，也就是说当是Model或者Map类型的参数时，它们会被赋值为同一个引用，所以实际操作的仍是同一个hash表
        return "";
    }

    // 这里加上params参数意思是要求请求中必须有json参数，那么同样的url如果params的值不一样，则被定为是不同的请求
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET, params = "json")
    @ResponseBody   // 用于支持请求可以返回json格式对象，一般用于ajax请求，因为它返回的json数据需要展现在页面中，如果普通请求，会直接打印json数据
    public User view(@PathVariable String id) {
        System.out.println(111);
        return map.get(id);
    }

    //  从后台往前台传递json对象方法：方法上加@ResponseBody注解或者直接在控制器上用@RestController注解
    //  从前台往后台直接传递深层次的json对象：
    //  1、普通简单对象可以直接用一个User对象来接收，无需加@RequestParams注解
    //  2、非普通数组对象则必须加@RequestParams注解
    //  3、复杂深层次嵌套对象则需要在参数前@RequestBody注解来解析json对象，
    //  当然可以在一个通用的方法前用@ModelAttribute来拦截所有请求，不管普通对象还是嵌套对象都会被解析为对应的对象
    //  4、复杂对象还可以从前台js中使用json.toString转为字符串传到后台，然后后台再使用JSONObject对象来解析对象
}
