/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Ha
 */
package dal;

import model.User;

import java.sql.*;

public class UserDAL {
    private Connection connection;

    public UserDAL(Connection connection) {
        this.connection = connection;
    }

    // Lấy user theo username để dùng login
    public User getUserByUsername(String username) throws SQLException {
        String sql = "SELECT u.uid, u.username, u.password, u.fullname, u.did, ur.rid, r.rname " +
                     "FROM [User] u " +
                     "LEFT JOIN User_Role ur ON u.uid = ur.uid " +
                     "LEFT JOIN Role r ON ur.rid = r.rid " +
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
                    user.setDid(rs.getInt("did"));
                    user.setRid(rs.getInt("rid"));
                    user.setRname(rs.getString("rname"));
                    return user;
                }
            }
        }
        return null;
    }
}

