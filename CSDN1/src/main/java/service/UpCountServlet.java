//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package service;

import dao.ArticleInfoDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.ResultJSONUtils;

@WebServlet({"/upcount"})
public class UpCountServlet extends HttpServlet {
    public UpCountServlet() {
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int succ = -1;
        String msg = "";
        int id = Integer.parseInt(req.getParameter("id"));
        if (id > 0) {
            ArticleInfoDao articleInfoDao = new ArticleInfoDao();

            try {
                succ = articleInfoDao.upcount(id);
            } catch (SQLException var8) {
                var8.printStackTrace();
            }
        } else {
            msg = "非法参数";
        }

        HashMap<String, Object> result = new HashMap();
        result.put("succ", succ);
        result.put("msg", msg);
        ResultJSONUtils.writeMap(resp, result);
    }
}
