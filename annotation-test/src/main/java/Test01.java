import java.lang.annotation.*;

/*
* @Author wwt
* @Date 23:01 2020/12/26
* @Desp 认识四种元注解
**/
// 使用注解
@MyAnnotation
public class Test01 {

}

// 定义一个注解
// 注解可以用在哪些地方
@Target(value = {ElementType.METHOD,ElementType.TYPE})
// 注解在什么地方有效，一般是Runtime
@Retention(value = RetentionPolicy.RUNTIME)
// 将注解生成在JavaDoc中
@Documented
// 子类继承父类注解
@Inherited
// 定义一个注解
@interface MyAnnotation{

}