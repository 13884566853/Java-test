package dome;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wwt
 * @Date: 2020/12/27 12:30
 * @Description:
 */
public class MyDao {
    // 测试
    @Sql("insert into user values(3,'zhang22','654321',18,'1848451315');")
    public static void excute(String sql) throws SQLException,ClassNotFoundException {
        //获取连接
        Connection conn = DbUtil.getConnection();
        Statement stat = conn.createStatement();
        stat.execute(sql);
    }
    // 增加
    @Insert("insert into user values(")
    public static void insert(String sql) throws SQLException,ClassNotFoundException {
        Connection conn = DbUtil.getConnection();
        Statement stat = conn.createStatement();
        stat.execute(sql);
    }
    @Update(pre = "update user ",suff = " where id = ")
    public void update(String sql)  throws SQLException {
        Connection conn = DbUtil.getConnection();
        Statement stat = conn.createStatement();
        stat.execute(sql);
    }

    @Delete("delete from user where id = ")
    public void delete(String sql) throws SQLException{
        Connection conn = DbUtil.getConnection();
        Statement stat = conn.createStatement();
        stat.execute(sql);

    }
    @SelectById("select id,username,password,age,phone from user where id =")
    public static UserBean select(String sql) throws SQLException,ClassNotFoundException {
        //获取连接
        Connection conn = DbUtil.getConnection();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(sql) ;
        UserBean user = null;
        while(rs.next()){
            user = new UserBean();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setAge(rs.getInt("age"));
            user.setPhone(rs.getString("phone"));
        }
        return user;
    }
    @SelectAll("select id,username,password,age,phone from user")
    public List<UserBean> selectAll(String sql) throws SQLException {
        List<UserBean> list = new ArrayList<UserBean>();
        Connection conn = DbUtil.getConnection();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery(sql) ;
        UserBean user = null;
        while(rs.next()){
            user = new UserBean();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setAge(rs.getInt("age"));
            user.setPhone(rs.getString("phone"));
            list.add(user);
        }
        return list;
    }
}