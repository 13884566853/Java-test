import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: wwt
 * @Date: 2020/12/26 22:52
 * @Description:注解的传参
 */
@MyAnnotation2(name = "wqer")
public class Test02 {
}
@MyAnnotation3
class Test02_2 {
}
@Target(value = {ElementType.TYPE,ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@interface MyAnnotation2{
    // 默认值名是value
    String value() default "";
    // 定义变量后面要加()
    String name();
    // 可以给变量赋默认值
    int age() default 18;
}
@Target(value = {ElementType.TYPE,ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@interface MyAnnotation3{
    // 默认值名是value
    String value() default "默认值";
}

