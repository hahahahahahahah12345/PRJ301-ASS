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
import java.util.List;

@WebServlet(name = "ListRequestServlet", urlPatterns = {"/request/list"})
public class ListRequestServlet extends HttpServlet {
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if(user == null){
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        RequestDAL requestDAL = new RequestDAL(connection);
        // TODO: Nếu là quản lý, lấy cả đơn của cấp dưới (chưa triển khai)
        List<Request> requests = requestDAL.getByUserId(user.getUid());
        req.setAttribute("requests", requests);
        req.getRequestDispatcher("/WEB-INF/views/request_list.jsp").forward(req, resp);
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
