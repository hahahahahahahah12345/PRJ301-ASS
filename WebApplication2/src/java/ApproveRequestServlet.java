/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controller;

import dal.Requestdal;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Request;
import model.User;

import java.io.IOException;

@WebServlet(name = "ApproveRequestServlet", urlPatterns = {"/request/approve"})
public class ApproveRequestServlet extends HttpServlet {

    private RequestDAO requestDAO = new RequestDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null) {
            response.sendRedirect("list");
            return;
        }

        int id = Integer.parseInt(idStr);
        Request req = requestDAO.getRequestById(id);
        request.setAttribute("requestObj", req);
        request.getRequestDispatcher("/approve_request.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action"); // Approve or Reject
        String comment = request.getParameter("comment");

        HttpSession session = request.getSession();
        User manager = (User) session.getAttribute("user");

        if ("Approve".equals(action)) {
            requestDAO.approveRequest(id, manager.getId(), comment);
        } else if ("Reject".equals(action)) {
            requestDAO.rejectRequest(id, manager.getId(), comment);
        }

        response.sendRedirect("list");
    }
}
