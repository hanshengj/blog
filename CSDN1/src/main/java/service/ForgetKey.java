package service;

import dao.UserInfoDao;
import models.UserInfo;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Crtated with IntelliJ IDEA.
 * Destcription:
 * User: hp
 * Date: 2021-05-22
 * Time: 10:34
 */
public class ForgetKey extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int state = -1;
        String msg = "";
        String idcard = request.getParameter("idnumber");
           if(idcard!=null){
               UserInfo userInfo = new UserInfo();
               userInfo.setIdcard(idcard);
               UserInfoDao userInfoDao = new UserInfoDao();
               try {
                   boolean res = userInfoDao.haveId(userInfo);

                   if (res) {
                       state = 200;
                   }
               } catch (SQLException throwables) {
                   throwables.printStackTrace();

               }
           }else{
               msg = "非法操作，你的身份无法验证";
           }
        //PrintWriter writer = response.getWriter();
        //writer.println("{\"state\":" + state +" ,\"msg\":\" " + msg + " \"}");
        HashMap<String, Object> result = new HashMap<>();
        result.put("state", state);
        result.put("msg", msg);
        ResultJSONUtils.writeMap(response, result);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doPost(request,response);
    }
}
