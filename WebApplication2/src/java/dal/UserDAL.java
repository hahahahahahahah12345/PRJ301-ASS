package dal;

import model.User;

import java.sql.*;

public class UserDAL {
    private Connection connection;

    public UserDAL(Connection connection) {
        this.connection = connection;
    }

    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT u.uid, u.username, u.password, u.fullname, u.did, u.rid, u.manager_id, r.rname " +
                     "FROM [User] u " +
                     "LEFT JOIN Role r ON u.rid = r.rid " +
                     "WHERE u.username = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUid(rs.getInt("uid"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setFullname(rs.getString("fullname"));
                    user.setDepId(rs.getInt("did"));
                    user.setRoleId(rs.getInt("rid"));
                    user.setManagerId(rs.getInt("manager_id") != 0 ? rs.getInt("manager_id") : null);
                    user.setRname(rs.getString("rname"));
                    return user;
                }
            }
        }
        return null;
    }
}