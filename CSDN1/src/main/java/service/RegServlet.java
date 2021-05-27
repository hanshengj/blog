package service;

import dao.UserInfoDao;
import models.UserInfo;
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
 * Date: 2021-04-03
 * Time: 10:30
 */
public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        int state = -1;
        String msg = "";
        //从前端获取参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String idcard = req.getParameter("idcard");
        PrintWriter writer = resp.getWriter();
        if (username == null || password == null || idcard==null) {
            //参数不正确
            msg = "参数不存在";

        } else {
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo.setPassword(password);
            userInfo.setIdcard(idcard);

            UserInfoDao userInfoDao = new UserInfoDao();

            try {
                boolean res = userInfoDao.add(userInfo);

                if (res) {
                    state = 200;
                } else {
                    state = 100;
                    msg = "客户已存在";
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();

            }
        }
        //writer.println("{\"state\":" + state +" ,\"msg\":\" " + msg + " \"}");
        // 3.将信息返回给前台
        HashMap<String, Object> result = new HashMap<>();
        result.put("state", state);
        result.put("msg", msg);
        ResultJSONUtils.writeMap(resp, result);
    }
}
/*

 */
