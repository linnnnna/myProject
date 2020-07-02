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
