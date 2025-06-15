/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Ha
 */
package controller;

import dal.RequestDAL;
import model.Request;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "ApproveRequestServlet", urlPatterns = {"/request/approve"})
public class ApproveRequestServlet extends HttpServlet {
    private Connection connection;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(
                    "jdbc:sqlserver://localhost:1433;databaseName=YourDBName;user=sa;password=YourPassword");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    // Hiển thị form duyệt đơn
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String reqIdStr = req.getParameter("id");
        if (reqIdStr == null) {
            resp.sendRedirect(req.getContextPath() + "/request/list");
            return;
        }

        try {
            int requestId = Integer.parseInt(reqIdStr);
            RequestDAL requestDAL = new RequestDAL(connection);
            Request request = requestDAL.getById(requestId);

            if (request == null) {
                resp.sendRedirect(req.getContextPath() + "/request/list");
                return;
            }

            // TODO: Kiểm tra user có phải quản lý trực tiếp của người tạo request không

            req.setAttribute("request", request);
            req.getRequestDispatcher("/WEB-INF/views/request_approve.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            throw new ServletException(e);
        }
    }

    // Xử lý duyệt đơn
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String reqIdStr = req.getParameter("id");
        String action = req.getParameter("action"); // Approve hoặc Reject
        String comment = req.getParameter("comment");

        if (reqIdStr == null || action == null) {
            resp.sendRedirect(req.getContextPath() + "/request/list");
            return;
        }

        try {
            int requestId = Integer.parseInt(reqIdStr);
            RequestDAL requestDAL = new RequestDAL(connection);
            Request request = requestDAL.getById(requestId);

            if (request == null) {
                resp.sendRedirect(req.getContextPath() + "/request/list");
                return;
            }

            // TODO: Kiểm tra quyền duyệt (user là quản lý trực tiếp của người tạo request)

            if ("approve".equalsIgnoreCase(action)) {
                request.setStatus("Approved");
            } else if ("reject".equalsIgnoreCase(action)) {
                request.setStatus("Rejected");
            }

            request.setProcessedBy(user.getUid());
            request.setComment(comment);

            boolean updated = requestDAL.update(request);
            if (updated) {
                resp.sendRedirect(req.getContextPath() + "/request/list");
            } else {
                req.setAttribute("error", "Cập nhật trạng thái đơn thất bại");
                req.setAttribute("request", request);
                req.getRequestDispatcher("/WEB-INF/views/request_approve.jsp").forward(req, resp);
            }

        } catch (NumberFormatException e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void destroy() {
        try {
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.destroy();
    }
}
