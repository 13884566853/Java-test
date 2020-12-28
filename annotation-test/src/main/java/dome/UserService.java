package dome;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wwt
 * @Date: 2020/12/27 15:04
 * @Description:
 */
public class UserService {
    @Test
    public void fun() throws Exception{
        UserBean user = new UserBean(15,"hanshisan","325",33,"17794214739");
        //addUser(user);
        updateUser(15,user);
        //delUser(3);
        //System.out.println(getUserById(1));
        //System.out.println(getAllUser());
    }
    static MyDao dao = new MyDao();
    //增加
    public void addUser0(UserBean user) throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Class c1 = user.getClass();
        Class c2 = dao.getClass();
        // TableName是注解的名称
        //TableName tableName = (TableName) c1.getAnnotation(TableName.class);
        // System.out.println(tableName.value());
        //Method methods[] = c2.getDeclaredMethods();
        Method method = c2.getDeclaredMethod("excute", String.class);
        Sql iSql = (Sql)method.getAnnotation(Sql.class);
        //c2.getMethod("insert",null);
        String sql = iSql.value();
        method.invoke(dao,sql);
    }
    //增加
    public static void addUser(UserBean user) throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class c2 = dao.getClass();
        // System.out.println(tableName.value());
        //Method methods[] = c2.getDeclaredMethods();
        Method method = c2.getDeclaredMethod("insert",String.class);
        Insert insert = (Insert)method.getAnnotation(Insert.class);
        //c2.getMethod("insert",null);
        StringBuffer sb = new StringBuffer();
        sb.append(insert.value());
        sb.append(" "+user.getId());
        sb.append(","+transDbColumn(user.getUsername()));
        sb.append(","+transDbColumn(user.getPassword()));
        sb.append(","+user.getAge());
        sb.append(","+transDbColumn(user.getPhone()));
        sb.append(")");
        System.out.println(sb.toString());
        method.invoke(dao,sb.toString());

    }
    public void updateUser(int id,UserBean user) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class c2 = dao.getClass();
        Method method = c2.getDeclaredMethod("update",String.class);
        Update update = (Update)method.getAnnotation(Update.class);
        StringBuffer sb = new StringBuffer();
        sb.append(update.pre());
        sb.append(" set username ="+transDbColumn(user.getUsername()));
        sb.append(", password ="+transDbColumn(user.getPassword()));
        sb.append(", age ="+user.getAge());
        sb.append(" ,phone ="+transDbColumn(user.getPhone()));
        sb.append(""+update.suff());
        sb.append(id);
        System.out.println(sb.toString());
        method.invoke(dao,sb.toString());
    }

    public void delUser(int id) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class c2 = dao.getClass();
        Method method = c2.getDeclaredMethod("delete",String.class);
        Delete delete = (Delete)method.getAnnotation(Delete.class);
        String sql = delete.value()+id;
        method.invoke(dao,sql);
    }

    public UserBean getUserById(int id) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class c2 = dao.getClass();
        Method method = c2.getDeclaredMethod("select",String.class);
        SelectById selectById = (SelectById)method.getAnnotation(SelectById.class);
        String sql = selectById.value()+id;
        return (UserBean) method.invoke(dao,sql);
    }

    public List<UserBean> getAllUser() throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class c2 = dao.getClass();
        // 重载方法就是通过参数来区分的
        Method method = c2.getDeclaredMethod("selectAll",String.class);
        SelectAll selectAll = (SelectAll)method.getAnnotation(SelectAll.class);
        String sql = selectAll.value();
        return (List<UserBean>) method.invoke(dao,sql);
    }
    public static String transDbColumn(String str){
        return "'"+str+"'";
    }



}