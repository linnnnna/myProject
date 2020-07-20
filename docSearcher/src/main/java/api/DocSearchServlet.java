package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import searcher.Result;
import searcher.Searcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DocSearchServlet extends HttpServlet {
    private Searcher searcher = new Searcher();
    private Gson gson = new GsonBuilder().create();

    public DocSearchServlet() throws IOException {
    }



    //而HttpServlet类已经实现了Servlet接口的所有方法，编写Servlet时，只需要继承HttpServlet，
    // 重写你需要的方法即可，并且它在原有Servlet接口上添加了一些与HTTP协议处理方法，
    // 它比Servlet接口的功能更为强大。
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=utf-8");
        String query = req.getParameter("query");
        if (query == null || query.equals("")) {
            resp.setStatus(404);
            resp.getWriter().write("query 参数非法");
            return;
        }
        List<Result> results = searcher.search(query);
        String respString = gson.toJson(results);
        resp.getWriter().write(respString);
    }
}
