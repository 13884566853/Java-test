package com.example.test.myspring.demo.mvc;

import com.example.test.myspring.demo.service.IDemoService;
import com.example.test.myspring.mvcframework.annotation.Autowired;
import com.example.test.myspring.mvcframework.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: wwt
 * @Date: 2020/12/29 23:38
 * @Description:
 */
public class DemoAction {
    @Autowired
    IDemoService demoService;

    @RequestMapping("/query")
    public void query(HttpServletRequest req, HttpServletResponse resp,String name,String id){
        String result = "获取到名称：" + name + "id："+id;
        try {
            resp.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
