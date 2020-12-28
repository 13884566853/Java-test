package dome;

/**
 * @Auther: wwt
 * @Date: 2020/12/27 11:24
 * @Description:
 */
import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * @Auther: wwt
 * @Date: 2020/12/27 01:19
 * @Description:基于注解和反射操作数据库
 */

public class Test06 {
    public static void main(String[] args) throws  Exception{
        UserBean user = new UserBean(3,"zhang3","123456",18,"17794214739");
        UserBean user2 = new UserBean(1,"zhang2","123456",18,"17794214709");

        Dao dao = new Dao();
//        dao.addUser(user);
//        dao.updateUser(user2);
        dao.delUser(3);
        System.out.println(dao.getAllUser());
//      dao.addUser(user);



    }

    @Insert( "insert into user values(2,'zhang22','654321',18,'1848451315');")
    public void add(UserBean user) {

    }

    public void get(String id){

    }
    public void update(UserBean user){

    }
    public void delete(String id){

    }
}
//@interface Insert{
//    String[] fields();
//}
//@interface Delete{
//    String id();
//}
//@interface Update{
//    String[] fields();
//}
//@interface SelectAll{
//
//}
//@interface SelectById{
//    String id();
//}

// 类的注解
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@interface TableName{
    String value();
}

// 方法的注解
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@interface Sql{
    String value();
}
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@interface Insert{
    String value();
}
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@interface Update{
    String pre();
    String suff();
}
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@interface Delete{
    String value();
}
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@interface SelectById{
    String value();
}
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@interface SelectAll{
    String value();
}

// 属性的注解
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@interface FieldBean{
    String columName();
    String type();
    int length();
}

