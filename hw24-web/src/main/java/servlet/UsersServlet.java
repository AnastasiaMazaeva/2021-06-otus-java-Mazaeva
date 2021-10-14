package servlet;

import com.google.gson.Gson;
import crm.model.Client;
import crm.service.DBServiceClient;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.TemplateProcessor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class UsersServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String TEMPLATE_ATTR_RANDOM_USER = "users";

    private final TemplateProcessor templateProcessor;
    private final DBServiceClient dbServiceClient;
    private final Gson gson;

    public UsersServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient, Gson gson) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(TEMPLATE_ATTR_RANDOM_USER, dbServiceClient.findAll());
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Client client = gson.fromJson(req.getReader(), Client.class);
        dbServiceClient.save(client);
    }

}
