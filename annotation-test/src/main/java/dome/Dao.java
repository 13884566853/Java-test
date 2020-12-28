package dome;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wwt
 * @Date: 2020/12/27 11:26
 * @Description:
 */
public class Dao {
    //增加
    public void addUser(UserBean user) throws SQLException {
        //获取连接
        Connection conn = DbUtil.getConnection();
        //sql
        String sql = "INSERT INTO user(id,username, password, age, phone)"
                +"values("+"?,?,?,?,?)";
        //预编译
        PreparedStatement ptmt = conn.prepareStatement(sql); //预编译SQL，减少sql执行

        //传参
        ptmt.setInt(1, user.getId());
        ptmt.setString(2, user.getUsername());
        ptmt.setString(3, user.getPassword());
        ptmt.setInt(4, user.getAge());
        ptmt.setString(5, user.getPhone());
        //执行
        ptmt.execute();
    }
    public void updateUser(UserBean user)  throws SQLException {
        //获取连接
        Connection conn = DbUtil.getConnection();
        //sql, 每行加空格
        String sql = "update user" +
                " set username=?,password=?,  age=?, phone=?"+
                " where id=?";
        //预编译
        PreparedStatement ptmt = conn.prepareStatement(sql); //预编译SQL，减少sql执行

        //传参
        ptmt.setString(1, user.getUsername());
        ptmt.setString(2, user.getPassword());
        ptmt.setInt(3, user.getAge());
        ptmt.setString(4, user.getPhone());
        ptmt.setInt(5, user.getId());

        //执行
        ptmt.execute();
    }

    public void delUser(int id) throws SQLException{
        //获取连接
        Connection conn = DbUtil.getConnection();
        //sql, 每行加空格
        String sql = "delete from user where id=?";
        //预编译SQL，减少sql执行
        PreparedStatement ptmt = conn.prepareStatement(sql);

        //传参
        ptmt.setInt(1, id);

        //执行
        ptmt.execute();
    }

    public List<UserBean> getAllUser() throws SQLException {
        Connection conn = DbUtil.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM user");

        List<UserBean> list = new ArrayList<UserBean>();
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

    public UserBean getUserByid(int id) throws SQLException {
        UserBean user = null;
        //获取连接
        Connection conn = DbUtil.getConnection();
        //sql, 每行加空格
        String sql = "select * from  user where id=?";
        //预编译SQL，减少sql执行
        PreparedStatement ptmt = conn.prepareStatement(sql);
        //传参
        ptmt.setInt(1, id);
        //执行
        ResultSet rs = ptmt.executeQuery();
        while(rs.next()){
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setAge(rs.getInt("age"));
            user.setPhone(rs.getString("phone"));
        }
        return user;
    }
}
