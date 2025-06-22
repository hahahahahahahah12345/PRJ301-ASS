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

    public boolean add(Request request) throws SQLException {
        String sql = "INSERT INTO Request (fromDate, toDate, reason, status, createdBy) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, request.getFromDate());
            ps.setDate(2, request.getToDate());
            ps.setString(3, request.getReason());
            ps.setString(4, request.getStatus());
            ps.setInt(5, request.getCreatedBy());
            return ps.executeUpdate() > 0;
        }
    }

    public List<Request> getByUserId(int userId) throws SQLException {
        List<Request> list = new ArrayList<>();
        String sql = "SELECT r.*, u.fullname AS createdByName, u2.fullname AS processedByName " +
                     "FROM Request r " +
                     "LEFT JOIN [User] u ON r.createdBy = u.uid " +
                     "LEFT JOIN [User] u2 ON r.processedBy = u2.uid " +
                     "WHERE r.createdBy = ? OR u.manager_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
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
                    r.setComment(rs.getString("comment"));
                    list.add(r);
                }
            }
        }
        return list;
    }

    public boolean update(Request request) throws SQLException {
        String sql = "UPDATE Request SET status = ?, processedBy = ?, processedDate = ?, comment = ? WHERE requestId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, request.getStatus());
            ps.setObject(2, request.getProcessedBy());
            ps.setDate(3, request.getProcessedDate());
            ps.setString(4, request.getComment());
            ps.setInt(5, request.getRequestId());
            return ps.executeUpdate() > 0;
        }
    }

    public Request getById(int requestId) throws SQLException {
        String sql = "SELECT r.*, u.fullname AS createdByName, u2.fullname AS processedByName " +
                     "FROM Request r " +
                     "LEFT JOIN [User] u ON r.createdBy = u.uid " +
                     "LEFT JOIN [User] u2 ON r.processedBy = u2.uid " +
                     "WHERE r.requestId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Request r = new Request();
                    r.setRequestId(rs.getInt("requestId"));
                    r.setFromDate(rs.getDate("fromDate"));
                    r.setToDate(rs.getDate("toDate"));
                    r.setReason(rs.getString("reason"));
                    r.setStatus(rs.getString("status"));
                    r.setCreatedBy(rs.getInt("createdBy"));
                    r.setProcessedBy(rs.getInt("processedBy") != 0 ? rs.getInt("processedBy") : null);
                    r.setProcessedDate(rs.getDate("processedDate"));
                    r.setComment(rs.getString("comment"));
                    return r;
                }
            }
        }
        return null;
    }
}