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
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet(name = "CreateRequestServlet", urlPatterns = {"/request/create"})
public class CreateRequestServlet extends HttpServlet {
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

    // Hiển thị form tạo đơn (GET)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/request_create.jsp").forward(req, resp);
    }

    // Xử lý gửi form tạo đơn (POST)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if(user == null) {
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
            boolean success = requestDAL.add(request);

            if(success){
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
            if(connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.destroy();
    }
}
