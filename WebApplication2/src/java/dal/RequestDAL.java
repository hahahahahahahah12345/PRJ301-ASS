/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Ha
 */
package dal;

import model.Request;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestDAL {
    private Connection connection;

    public RequestDAL(Connection connection) {
        this.connection = connection;
    }

    // Thêm đơn nghỉ phép mới
    public boolean addRequest(Request request) throws SQLException {
        String sql = "INSERT INTO Request (fromDate, toDate, reason, status, createdBy) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, request.getFromDate());
            ps.setDate(2, request.getToDate());
            ps.setString(3, request.getReason());
            ps.setString(4, "Inprogress");
            ps.setInt(5, request.getCreatedBy());
            return ps.executeUpdate() > 0;
        }
    }

    // Lấy danh sách đơn theo người tạo
    public List<Request> getRequestsByUserId(int userId) throws SQLException {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT * FROM Request WHERE createdBy = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Request r = new Request();
                    r.setRequestId(rs.getInt("requestId"));
                    r.setFromDate(rs.getDate("fromDate"));
                    r.setToDate(rs.getDate("toDate"));
                    r.setReason(rs.getString("reason"));
                    r.setStatus(rs.getString("status"));
                    r.setCreatedBy(rs.getInt("createdBy"));
                    r.setProcessedBy(rs.getInt("processedBy") != 0 ? rs.getInt("processedBy") : null);
                    r.setProcessedDate(rs.getDate("processedDate"));
                    list.add(r);
                }
            }
        }
        return list;
    }

    // Cập nhật trạng thái duyệt đơn
    public boolean updateRequestStatus(int requestId, String status, int processedBy, Date processedDate) throws SQLException {
        String sql = "UPDATE Request SET status = ?, processedBy = ?, processedDate = ? WHERE requestId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, processedBy);
            ps.setDate(3, processedDate);
            ps.setInt(4, requestId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean add(Request request) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public List<Request> getByUserId(int uid) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean update(Request request) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Request getById(int requestId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
