/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Ha
 */
package dal;

import model.Feature;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeatureDAL {
    private final Connection connection;

    public FeatureDAL(Connection connection) {
        this.connection = connection;
    }

    // Lấy tất cả feature
    public List<Feature> getAllFeatures() throws SQLException {
        List<Feature> features = new ArrayList<>();
        String sql = "SELECT fid, url FROM Feature";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Feature f = new Feature();
                f.setFid(rs.getInt("fid"));
                f.setUrl(rs.getString("url"));
                features.add(f);
            }
        }
        return features;
    }

    // Lấy feature theo role id (phân quyền)
    public List<Feature> getFeaturesByRoleId(int roleId) throws SQLException {
        List<Feature> features = new ArrayList<>();
        String sql = "SELECT f.fid, f.url FROM Feature f " +
                "INNER JOIN Role_Feature rf ON f.fid = rf.fid " +
                "WHERE rf.rid = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Feature f = new Feature();
                    f.setFid(rs.getInt("fid"));
                    f.setUrl(rs.getString("url"));
                    features.add(f);
                }
            }
        }
        return features;
    }

    // Kiểm tra 1 user có quyền truy cập vào feature url không
    public boolean hasAccess(int roleId, String featureUrl) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Feature f " +
                "INNER JOIN Role_Feature rf ON f.fid = rf.fid " +
                "WHERE rf.rid = ? AND f.url = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ps.setString(2, featureUrl);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}
