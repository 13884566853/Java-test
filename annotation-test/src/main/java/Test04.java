import java.lang.reflect.Constructor;

/**
 * @Auther: wwt
 * @Date: 2020/12/27 00:16
 * @Description: 反射创建对象
 */
public class Test04 {
    public static void main(String[] args) throws Exception{
        Class c1 = Class.forName("User");
        // 无参的构造器，构造方法
        Constructor constructor1 = c1.getConstructor();
        User user1 = (User)constructor1.newInstance();
        System.out.println(user1.toString());
        // 有参的构造器，构造方法
        Constructor constructor2 = c1.getConstructor(String.class,String.class);
        //constructor2.setAccessible(true);//关闭权限检查，提升性能
        User user2 = (User)constructor2.newInstance("张三","123456");
        System.out.println(user2.toString());
    }
}
class User{
    public String username;
    public String password;
    public String getUsername() {
        return username;
    }
    public User() {

    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}