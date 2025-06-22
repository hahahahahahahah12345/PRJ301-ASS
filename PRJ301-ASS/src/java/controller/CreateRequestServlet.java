package controller;

import dal.RequestDAL;
import model.Request;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "CreateRequestServlet", urlPatterns = {"/request/create"})
public class CreateRequestServlet extends HttpServlet {
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
        req.getRequestDispatcher("/WEB-INF/views/request_create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String fromDateStr = req.getParameter("fromDate");
        String toDateStr = req.getParameter("toDate");
        String reason = req.getParameter("reason");

        try {
            Date fromDate = Date.valueOf(fromDateStr);
            Date toDate = Date.valueOf(toDateStr);

            Request request = new Request();
            request.setFromDate(fromDate);
            request.setToDate(toDate);
            request.setReason(reason);
            request.setCreatedBy(user.getUid());
            request.setStatus("Inprogress");

            RequestDAL requestDAL = new RequestDAL(connection);
            boolean success = false;
            try {
                success = requestDAL.add(request);
            } catch (SQLException ex) {
                Logger.getLogger(CreateRequestServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (success) {
                resp.sendRedirect(req.getContextPath() + "/request/list");
            } else {
                req.setAttribute("error", "Tạo đơn thất bại");
                req.getRequestDispatcher("/WEB-INF/views/request_create.jsp").forward(req, resp);
            }
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", "Ngày không hợp lệ");
            req.getRequestDispatcher("/WEB-INF/views/request_create.jsp").forward(req, resp);
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