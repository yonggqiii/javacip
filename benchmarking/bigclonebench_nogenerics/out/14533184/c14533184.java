class c14533184 {

    public boolean delete(int id) {
        boolean deletionOk = false;
        Connection conn = null;
        try {
            conn = JavaCIPUnknownScope.db.getConnection();
            conn.setAutoCommit(false);
            String sql = "DELETE FROM keyphrases WHERE website_id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            deletionOk = ps.executeUpdate() == 1;
            ps.close();
            sql = "DELETE FROM websites WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            boolean success = ps.executeUpdate() == 1;
            deletionOk = deletionOk && success;
            ps.close();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLRuntimeException sqle) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLRuntimeException sex) {
                throw new OsseoFailure("SQL error: roll back failed. ", sex);
            }
            throw new OsseoFailure("SQL error: cannot remove website with id " + id + ".", sqle);
        } finally {
            JavaCIPUnknownScope.db.putConnection(conn);
        }
        return deletionOk;
    }
}
