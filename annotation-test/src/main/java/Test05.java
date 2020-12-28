import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * @Auther: wwt
 * @Date: 2020/12/27 00:44
 * @Description:反射操作注解
 */
public class Test05 {
    public static void main(String[] args) throws  Exception{
        Class c1 = Class.forName("MyUser");
        Annotation annotations[] = c1.getAnnotations();

        TableUser userAnno = (TableUser) c1.getAnnotation(TableUser.class);
        String value = userAnno.value();
        System.out.printf(value);
        // 获得类指定的注解
        Field id = c1.getDeclaredField("id");
        FieldUser fieldAnno = (FieldUser) id.getAnnotation(FieldUser.class);
        String columName = fieldAnno.columName();
        String type = fieldAnno.type();
        int length = fieldAnno.length();
        System.out.printf("%s %s %d",columName,type,length);
    }
}
@TableUser("user")
class MyUser{
    @FieldUser(columName = "db_id",type = "varchar",length = 64)
    public  String id;
    @FieldUser(columName = "db_age",type = "number",length = 3)
    public int age;
}

// 类的注解
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@interface TableUser{
    String value();
}
// 属性的注解
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@interface FieldUser{
    String columName();
    String type();
    int length();
}
