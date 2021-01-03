package com.example.test.myspring.mvcframework.servlet;

import com.example.test.myspring.mvcframework.annotation.Controller;
import com.example.test.myspring.mvcframework.annotation.RequestMapping;
import com.example.test.myspring.mvcframework.annotation.RequestParam;
import com.example.test.myspring.mvcframework.annotation.Service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * @Auther: wwt
 * @Date: 2020/12/28 23:23
 * @Description:
 */
public class DispatcherServlet extends HttpServlet {

    private Map<String,Object> ioc = new HashMap<String,Object>();

    private Properties contextConfig = new Properties();
    private List<String> classNames = new ArrayList<String>();
    private Map<String, Method> handlerMapping = new HashMap<String, Method>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 调用
        super.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        // 真正的调用，负责请求转发
        try {
            doDispatch(req,resp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath,"").replaceAll("/+","/");

        if(!this.handlerMapping.containsKey(url)){
            resp.getWriter().write("404 Not Found!!");
            return;
        }

        Method method = this.handlerMapping.get(url);

        Map<String,String[]> paramsMap = req.getParameterMap();

        //方法两类：形参列表
        Class<?> [] paramterTypes = method.getParameterTypes();
        //        实参
        Object [] paramValues = new Object[paramterTypes.length];

        //给实参列表填值
        for (int i = 0; i < paramterTypes.length; i++) {
            Class paramterType = paramterTypes[i];
            if(paramterType == HttpServletRequest.class){
                paramValues[i] = req;
            }else if(paramterType == HttpServletResponse.class){
                paramValues[i] = resp;
            }else if(paramterType == String.class){
                Annotation[][] pa = method.getParameterAnnotations();
                for (Annotation a : pa[i]) {
                    if(a instanceof RequestParam){
                        String paramName = ((RequestParam) a).value();
                        if(!"".equals(paramName.trim())){
                            //[1 , 2 , 3]
                            String value = Arrays.toString(paramsMap.get(paramName))
                                    .replaceAll("\\[|\\]","")
                                    .replaceAll("\\s","");
                            paramValues[i] = value;
                        }
                    }
                }
            }
        }


        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
        //硬编码
//        Map<String,String[]> params = req.getParameterMap();
//        method.invoke(ioc.get(beanName),new Object[]{req,resp,params.get("name")[0],params.get("id")[0]});
        method.invoke(ioc.get(beanName),paramValues);
    }
//    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
//        String url = req.getRequestURI();
//        String contextPath = req.getContextPath();
//        url = url.replaceAll(contextPath,"").replaceAll("/+","/");
//        if (!handlerMapping.containsKey(url)){
//            resp.getWriter().write("404 Not Found!");
//            return;
//        }
//
//        Map<String,String[]> params = req.getParameterMap();
//
//        Method method = handlerMapping.get(url);
//        String beanName = toLowerFirstCase(method.getDeclaringClass().getSimpleName());
//
//        method.invoke(ioc.get(beanName),new Object[]{req,resp,params.get("name")[0],params.get("id")[0]});
//    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 1.加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        // 2.初始化IOC容器,扫描相关的类
        doScanner(contextConfig.getProperty("scanPackage"));
        // 3.实例化相关的类，并且将实例对象缓存到IoC容器中
        doInstance();
        // 4.完成自动复制(依赖注入)
        doAutowired();
        // 5.初始化HandlerMapping
        doInitHandlerMapping();

        System.out.println("SpringFarmwork init complate!!!");
        super.init(config);
    }

    private void doLoadConfig(String contextConfigLocation) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);

        try {
            contextConfig.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    * @Author wwt
    * @Date 23:54 2020/12/28
    * @Desp 扫描包名和类名，根据包名类名反射生成对象
    **/
    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.","/"));
        // 包路径对应的文件路径，ClassPath
        File classPath = new File(url.getFile());
        for (File file: classPath.listFiles()) {
            if (file.isDirectory()){
                doScanner(scanPackage + "." + file.getName());
            }else {
                // 确保存的类名都是.class 文件的
                if (!file.getName().endsWith(".class")){
                    continue;
                }
                // Class.forName()
                String className = (scanPackage + "." + file.getName().replaceAll(".class", ""));
                classNames.add(className);
            }
        }
    }

    /*
    * @Author wwt
    * @Date 21:56 2020/12/29
    * @Desp 创建对象并添加至ioc容器中
    * 需要解决的问题：1.不同包重名
    *                 2.自动注入接口
    **/
    private void doInstance() {

        if (classNames.isEmpty()){
            return;
        }
        for (String className: classNames ) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(className);
                // 有注解的自动注入
                if (clazz.isAnnotationPresent(Controller.class)){
                    // 1.默认类名首字母小写
                    Object instance = clazz.newInstance();
                    String beanName = toLowerFirstCase(clazz.getSimpleName());
                    ioc.put(beanName,instance);
                    // 2.如果在不同包下出现相同类名，利用自定义注解
                }else if (clazz.isAnnotationPresent(Service.class)){
                    String beanName = toLowerFirstCase(clazz.getSimpleName());

                    //2、不同包下重名，自定义beanName
                    Service service = clazz.getAnnotation(Service.class);
                    if (!"".equals(service.value())) {
                        beanName = service.value();
                    }

                    Object instance = clazz.newInstance();
                    ioc.put(beanName, instance);
                    //3、类型的全类名
                    for (Class<?> i : clazz.getInterfaces()) {
                        //一个接口有多个实现类
                        if(ioc.containsKey(i.getName())){
                            throw new Exception("The beanName is exists!!");
                        }
                        ioc.put(i.getName(),instance);
                    }

                }else{
                    continue;
                }
                Object instance = clazz.newInstance();
                // 1.默认类名首字母小写
                String beanName = toLowerFirstCase(clazz.getSimpleName());
                ioc.put(beanName,instance);
                // 2.如果在不同包下出现相同类名，利用自定义注解
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /*
    * @Author wwt
    * @Date 21:58 2020/12/29
    * @Desp 如果ioc容器为空直接return，如果有就获取对象里面所有的字段
    * 如果字段有@Autowaird注解，那么就把属性注入进来
    **/
    private void doAutowired() {
    }

    /*
    * @Author wwt
    * @Date 22:12 2020/12/29
    * @Desp 将ioc容器里面的加了@Controller注解的类的@RequestMapping的方法都拿出来
    *
    **/
    private void doInitHandlerMapping() {
        if (ioc.isEmpty()){
            return;
        }
        for (Map.Entry<String, Object> entry: ioc.entrySet()){
            Class<?> clazz = entry.getValue().getClass();

            String baseUrl = "";
            if (!clazz.isAnnotationPresent(RequestMapping.class)){
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                baseUrl = requestMapping.value();
            }

            for (Method method: clazz.getMethods() ) {
                // method是否加了RequestMapping注解
                if (!method.isAnnotationPresent(RequestMapping.class)){
                    return;
                }
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                String url = ("/"+baseUrl+"/"+requestMapping.value()).replaceAll("/+","/");
                handlerMapping.put(url,method);
                System.out.println("Mapped : " + url + " " + method);
            }
        }
    }

    private  String toLowerFirstCase(String simpleName){
        char [] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
