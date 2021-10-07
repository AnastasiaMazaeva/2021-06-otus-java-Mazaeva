package servlet;

import com.google.gson.Gson;
import crm.model.Client;
import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UserCreateServlet extends HttpServlet {

    private final UserDao userDao;
    private final Gson gson;

    public UserCreateServlet(UserDao userDao, Gson gson) {
        this.userDao = userDao;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Client client = gson.fromJson(req.getReader(), Client.class);
        Client savedClient = userDao.save(client);
        resp.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = resp.getOutputStream();
        out.print(gson.toJson(savedClient));
    }
}
