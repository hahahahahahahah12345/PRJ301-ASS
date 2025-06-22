package controller;

import dal.FeatureDAL;
import dal.RequestDAL;
import model.Request;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ListRequestServlet", urlPatterns = {"/request/list"})
public class ListRequestServlet extends HttpServlet {
    private Connection connection;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            connection = dal.DBContext.getConnection();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            FeatureDAL featureDAL = new FeatureDAL(connection);
            if (!featureDAL.hasAccess(user.getRoleId(), "/request/list")) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Không có quyền truy cập");
                return;
            }

            RequestDAL requestDAL = new RequestDAL(connection);
            List<Request> requests = requestDAL.getByUserId(user.getUid());
            req.setAttribute("requests", requests);
            req.getRequestDispatcher("/WEB-INF/views/request_list.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void destroy() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.destroy();
    }
}