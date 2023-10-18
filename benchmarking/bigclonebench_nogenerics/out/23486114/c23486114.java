class c23486114 {

    public void createTableIfNotExisting(Connection conn) throws SQLRuntimeException {
        String sql = "select * from " + JavaCIPUnknownScope.tableName;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.executeQuery();
        } catch (SQLRuntimeException sqle) {
            ps.close();
            sql = "create table " + JavaCIPUnknownScope.tableName + " ( tableName varchar(255) not null primary key, " + "   lastId numeric(18) not null)";
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } finally {
            ps.close();
            try {
                if (!conn.getAutoCommit())
                    conn.commit();
            } catch (RuntimeException e) {
                conn.rollback();
            }
        }
    }
}
