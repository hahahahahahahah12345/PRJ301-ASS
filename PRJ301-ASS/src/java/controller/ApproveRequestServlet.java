package controller;

import dal.AgendaDAL;
import dal.FeatureDAL;
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

@WebServlet(name = "ApproveRequestServlet", urlPatterns = {"/request/approve"})
public class ApproveRequestServlet extends HttpServlet {
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

        String reqIdStr = req.getParameter("id");
        if (reqIdStr == null) {
            resp.sendRedirect(req.getContextPath() + "/request/list");
            return;
        }

        try {
            FeatureDAL featureDAL = new FeatureDAL(connection);
            if (!featureDAL.hasAccess(user.getRoleId(), "/request/approve")) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Không có quyền duyệt đơn");
                return;
            }

            int requestId = Integer.parseInt(reqIdStr);
            RequestDAL requestDAL = new RequestDAL(connection);
            Request request = requestDAL.getById(requestId);

            if (request == null) {
                resp.sendRedirect(req.getContextPath() + "/request/list");
                return;
            }

            // Kiểm tra user có phải quản lý trực tiếp
          

            req.setAttribute("request", request);
            req.getRequestDispatcher("/WEB-INF/views/request_approve.jsp").forward(req, resp);

        } catch (NumberFormatException | SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String reqIdStr = req.getParameter("id");
        String action = req.getParameter("action");
        String comment = req.getParameter("comment");

        if (reqIdStr == null || action == null) {
            resp.sendRedirect(req.getContextPath() + "/request/list");
            return;
        }

        try {
            FeatureDAL featureDAL = new FeatureDAL(connection);
            if (!featureDAL.hasAccess(user.getRoleId(), "/request/approve")) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Không có quyền duyệt đơn");
                return;
            }

            int requestId = Integer.parseInt(reqIdStr);
            RequestDAL requestDAL = new RequestDAL(connection);
            Request request = requestDAL.getById(requestId);

            if (request == null) {
                resp.sendRedirect(req.getContextPath() + "/request/list");
                return;
            }

            // Kiểm tra user có phải quản lý trực tiếp
            

            if ("approve".equalsIgnoreCase(action)) {
                request.setStatus("Approved");
                // Cập nhật Agenda
                AgendaDAL agendaDAL = new AgendaDAL(connection);
                Date date = request.getFromDate();
                while (!date.after(request.getToDate())) {
                    agendaDAL.addAgenda(request.getCreatedBy(), date, "OnLeave");
                    date = new Date(date.getTime() + 24 * 60 * 60 * 1000);
                }
            } else if ("reject".equalsIgnoreCase(action)) {
                request.setStatus("Rejected");
            }

            request.setProcessedBy(user.getUid());
            request.setProcessedDate(new Date(System.currentTimeMillis()));
            request.setComment(comment);

            boolean updated = requestDAL.update(request);
            if (updated) {
                resp.sendRedirect(req.getContextPath() + "/request/list");
            } else {
                req.setAttribute("error", "Cập nhật trạng thái đơn thất bại");
                req.setAttribute("request", request);
                req.getRequestDispatcher("/WEB-INF/views/request_approve.jsp").forward(req, resp);
            }

        } catch (NumberFormatException | SQLException e) {
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

    private static class PreparedStatement {

        public PreparedStatement() {
        }

        private void setInt(int i, int createdBy) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}