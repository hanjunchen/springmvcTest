package cn.hsgene.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hjc on 2016/9/21.
 */
public class firstController extends AbstractController{// BeanNameUrlHandlerMapping方式才需要继承AbstractController

    // handleRequestInternal方法相当于struts中的excute方法，即不指定访问方法默认响应请求的方法
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception{
        System.out.println("test first springMVC!");
        return new ModelAndView("springmvc01");// 此处的参数是逻辑视图名，会交给InternalResourceViewResolver进行解析
    }

}
