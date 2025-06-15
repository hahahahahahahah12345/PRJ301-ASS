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
import java.time.LocalDate;

@WebServlet(name = "CreateRequestServlet", urlPatterns = {"/request/create"})
public class CreateRequestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/create_request.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String reason = request.getParameter("reason");

        Request req = new Request();
        req.setUserId(user.getId());
        req.setFromDate(LocalDate.parse(from));
        req.setToDate(LocalDate.parse(to));
        req.setReason(reason);
        req.setStatus("Inprogress");

        new Requestdal().createRequest(req);
        response.sendRedirect("list"); // sẽ tạo sau
    }
}

