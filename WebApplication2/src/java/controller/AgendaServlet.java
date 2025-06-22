package controller;

import dal.AgendaDAL;
import dal.FeatureDAL;
import model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AgendaServlet", urlPatterns = {"/agenda"})
public class AgendaServlet extends HttpServlet {
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
            try {
                if (!featureDAL.hasAccess(user.getRoleId(), "/agenda")) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Không có quyền xem Agenda");
                    return;
                }
            } catch (SQLException ex) {
                Logger.getLogger(AgendaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            String startDateStr = req.getParameter("startDate") != null ? req.getParameter("startDate") : "2025-01-01";
            String endDateStr = req.getParameter("endDate") != null ? req.getParameter("endDate") : "2025-01-09";

            Date startDate = Date.valueOf(startDateStr);
            Date endDate = Date.valueOf(endDateStr);

            AgendaDAL agendaDAL = new AgendaDAL(connection);
            List<User> staffs = agendaDAL.getUsersByDepartment(user.getDepId());
            List<String> agendaData = agendaDAL.getAgenda(user.getDepId(), startDate, endDate);

            Map<Integer, Map<String, String>> agendaMap = new HashMap<>();
            for (User staff : staffs) {
                agendaMap.put(staff.getUid(), new HashMap<>());
            }
            for (String data : agendaData) {
                String[] parts = data.split(":");
                int userId = Integer.parseInt(parts[0]);
                String date = parts[1];
                String status = parts[2];
                agendaMap.get(userId).put(date, status);
            }

            List<String> dates = new ArrayList<>();
            Date current = startDate;
            while (!current.after(endDate)) {
                dates.add(current.toString());
                current = new Date(current.getTime() + 24 * 60 * 60 * 1000);
            }

            req.setAttribute("staffs", staffs);
            req.setAttribute("dates", dates);
            req.setAttribute("agendaMap", agendaMap);
            req.setAttribute("startDate", startDateStr);
            req.setAttribute("endDate", endDateStr);
            req.getRequestDispatcher("/WEB-INF/views/agenda.jsp").forward(req, resp);

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