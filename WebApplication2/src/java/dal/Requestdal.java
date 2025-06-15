/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

public Request getRequestById(int id) {
    String sql = "SELECT * FROM [Request] WHERE id = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Request r = new Request();
            r.setId(rs.getInt("id"));
            r.setUserId(rs.getInt("user_id"));
            r.setFromDate(rs.getDate("from_date").toLocalDate());
            r.setToDate(rs.getDate("to_date").toLocalDate());
            r.setReason(rs.getString("reason"));
            r.setStatus(rs.getString("status"));
            r.setProcessedBy(rs.getInt("processed_by"));
            r.setComment(rs.getString("comment"));
            return r;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

public void approveRequest(int id, int managerId, String comment) {
    String sql = "UPDATE [Request] SET status = 'Approved', processed_by = ?, comment = ? WHERE id = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, managerId);
        ps.setString(2, comment);
        ps.setInt(3, id);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void rejectRequest(int id, int managerId, String comment) {
    String sql = "UPDATE [Request] SET status = 'Rejected', processed_by = ?, comment = ? WHERE id = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, managerId);
        ps.setString(2, comment);
        ps.setInt(3, id);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

