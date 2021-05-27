package dao;

import models.UserInfo;
import utils.DBUtils;
import utils.MD5Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * userinfo 的数据操作方法
 * 增、删、改、查
 */
public class UserInfoDao {

    /**
     * 添加方法
     * @param userInfo
     * @return
     */
    public boolean add(UserInfo userInfo) throws SQLException {
        boolean result = false;
        if(userInfo.getUsername()!=null
                && userInfo.getPassword()!=null && userInfo.getIdcard()!=null){
            // 正常的参数
            //System.out.println("参数正常");
            Connection connection = DBUtils.getConnect();
            //先判断该用户是否已经注册过了
            String sql1 = "select * from userinfo where username=? ";
            PreparedStatement preparedStatement=connection.prepareStatement(sql1);
            //使用MD5保存密码,密码转换位MD5
            String md51 = MD5Util.getMD5(userInfo.getPassword());
            userInfo.setPassword(md51);
            preparedStatement.setString(1,userInfo.getUsername());
            //preparedStatement.setString(2,userInfo.getPassword());
            //preparedStatement.setString(2,userInfo.getPassword());
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                //说明该用户已经存在了，就不能再注册
                // 关闭连接
                DBUtils.close(connection,preparedStatement,resultSet);

            }else{
                //未被注册
                String sql2 = "insert into userinfo(username,password,idcard) values(?,?,?)";
                PreparedStatement statement = connection.prepareStatement(sql2);

                //使用MD5保存密码和虚拟ID,虚拟ID转换位MD5
                String md52 = MD5Util.getMD5(userInfo.getIdcard());
                userInfo.setIdcard(md52);

                statement.setString(1,userInfo.getUsername());
                statement.setString(2,userInfo.getPassword());
                statement.setString(3,userInfo.getIdcard());
                result = statement.executeUpdate()>=1?true:false;
                // 关闭连接
                DBUtils.close(connection,statement,null);
            }
        }
        return result;
    }



   //判断这个ID是否存在，不存在则是非法操作不能改密码
   public static boolean haveId (UserInfo userInfo) throws SQLException {
       boolean result=false;
       if(userInfo.getIdcard()!=null){
           Connection connection=DBUtils.getConnect();
           //String temp = userInfo.getIdcard();
           String md5 = MD5Util.getMD5(userInfo.getIdcard());
           userInfo.setIdcard(md5);
           String sql = "select * from userinfo where idcard=? ";
           PreparedStatement statement = connection.prepareStatement(sql);
           statement.setString(1,userInfo.getIdcard());
           //preparedStatement.setString(2,userInfo.getPassword());
           ResultSet resultSet=statement.executeQuery();
           if(resultSet.next()){
               result = true;//合法操作
           }
           // 关闭连接
           DBUtils.close(connection,statement,resultSet);

       }
       return result;
   }

    //修改密码
    public static boolean reSetPassword (UserInfo userInfo) throws SQLException {
        boolean result=false;
        if(userInfo.getPassword()!=null &&userInfo.getUsername()!=null  ){
            Connection connection=DBUtils.getConnect();
            //String temp = userInfo.getIdcard();
            String md5 = MD5Util.getMD5(userInfo.getPassword());
            userInfo.setPassword(md5);
            String sql = "update userinfo set password=? where username=? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,userInfo.getPassword());
            statement.setString(2,userInfo.getUsername());
            //preparedStatement.setString(2,userInfo.getPassword());
            int n=statement.executeUpdate();
            if(n >= 1){
                result = true;//合法操作
            }
            // 关闭连接
            DBUtils.close(connection,statement,null);

        }
        return result;
    }



    public static boolean isLogin(UserInfo userInfo) throws SQLException {
        boolean result=false;
        if(userInfo.getPassword()!=null &&userInfo.getUsername()!=null
                && !userInfo.getUsername().equals("") && !userInfo.getPassword().equals("")){
            Connection connection=DBUtils.getConnect();
            String sql="select * from userinfo where username=? and password=?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            String md5 = MD5Util.getMD5(userInfo.getPassword());
            userInfo.setPassword(md5);
            preparedStatement.setString(1,userInfo.getUsername());
            preparedStatement.setString(2,userInfo.getPassword());
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                result=true;
            }
            DBUtils.close(connection,preparedStatement,resultSet);
        }
        // 关闭连接

        return result;
    }

    public UserInfo getUserInfo(UserInfo userInfo) throws SQLException {
        UserInfo user = new UserInfo();
        // todo:非空效验
        Connection connection = DBUtils.getConnect();
        String sql = "select * from userinfo where username=? " +
                "and password=? and state=1";
        PreparedStatement statement = connection.prepareStatement(sql);
        String md5 = MD5Util.getMD5(userInfo.getPassword());
        userInfo.setPassword(md5);
        statement.setString(1, userInfo.getUsername());
        statement.setString(2, userInfo.getPassword());
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            // 获取并设置id
            user.setId(resultSet.getInt("id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
        }
        DBUtils.close(connection,statement,resultSet);
        return user;
    }

}