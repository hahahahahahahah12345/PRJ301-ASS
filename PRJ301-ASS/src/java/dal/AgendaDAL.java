package dal;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendaDAL {
    private Connection connection;

    public AgendaDAL(Connection connection) {
        this.connection = connection;
    }

    public void addAgenda(int userId, Date date, String status) throws SQLException {
        String sql = "INSERT INTO Agenda (userId, date, status) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setDate(2, date);
            ps.setString(3, status);
            ps.executeUpdate();
        }
    }

    public List<User> getUsersByDepartment(int did) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT uid, fullname FROM [User] WHERE did = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, did);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = new User();
                    u.setUid(rs.getInt("uid"));
                    u.setFullname(rs.getString("fullname"));
                    users.add(u);
                }
            }
        }
        return users;
    }

    public List<String> getAgenda(int did, Date startDate, Date endDate) throws SQLException {
        List<String> agenda = new ArrayList<>();
        String sql = "SELECT a.userId, a.date, a.status " +
                     "FROM Agenda a " +
                     "JOIN [User] u ON a.userId = u.uid " +
                     "WHERE u.did = ? AND a.date BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, did);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    agenda.add(rs.getInt("userId") + ":" + rs.getDate("date") + ":" + rs.getString("status"));
                }
            }
        }
        return agenda;
    }
}