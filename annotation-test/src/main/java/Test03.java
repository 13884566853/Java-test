import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Auther: wwt
 * @Date: 2020/12/26 23:17
 * @Description: 反射的使用
 * 学习这一章的时候前驱知识：JVM类加载过程
 */
public class Test03 {
    public static void main(String[] args) throws Exception{
        Class c1 = Class.forName("Test");
        Method methods[] = c1.getMethods();

        System.out.println("获取到method[0]方法名 "+methods[0].getName());

        // 注意！，获取类的方法顺序与类定义方法的顺序不一定一致 多次执行结果会有所不同
        // 通过反射，调用空参方法和有参方法
        methods[0].invoke(new Test(),null);
        methods[1].invoke(new Test(),"aaa"); //String.Class
        System.out.println("============================");
        Class c2 = Class.forName("Person");
        Method method1 = c2.getMethod("setName",String.class);
        // 类加载的理解，我set参数李四，最后打出来还是张三，参考图片理解
        method1.invoke(new Person(),"李四");
        Method method2 = c2.getMethod("getName",null);
        System.out.println(method2.invoke(new Person()));
        // 获取字段
        Field f1 = c2.getDeclaredField("name");
        Field f2[] = c2.getFields();//获取权限为public的参数
        Field f3[] = c2.getDeclaredFields();//获取所有的参数
        System.out.println(f1);

        System.out.println("============================");
        for (int i = 0; i < f3.length ; i++) {
            System.out.println(f3[i]);
        }
    }
}

class Test{
    public void fun1(){
        System.out.println("method f1 running");
    }
    public void fun2(String str){
        System.out.println("method f1 running args:"+str);
    }
   }
class Person{
    public String name = "张三";
    private int age = 18;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}