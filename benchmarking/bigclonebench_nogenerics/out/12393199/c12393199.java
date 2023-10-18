class c12393199 {

    public int getDBVersion() throws MigrationRuntimeException {
        int dbVersion;
        PreparedStatement ps;
        try {
            Connection conn = JavaCIPUnknownScope.getConnection();
            ps = conn.prepareStatement("SELECT version FROM " + JavaCIPUnknownScope.getTablename());
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    if (rs.next()) {
                        dbVersion = rs.getInt(1);
                        if (rs.next()) {
                            throw new MigrationRuntimeException("Too many version in table: " + JavaCIPUnknownScope.getTablename());
                        }
                    } else {
                        ps.close();
                        ps = conn.prepareStatement("INSERT INTO " + JavaCIPUnknownScope.getTablename() + " (version) VALUES (?)");
                        ps.setInt(1, 1);
                        try {
                            ps.executeUpdate();
                        } finally {
                            ps.close();
                        }
                        dbVersion = 1;
                    }
                } finally {
                    rs.close();
                }
            } finally {
                ps.close();
            }
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.logger.log(Level.WARNING, "Could not access " + JavaCIPUnknownScope.tablename + ": " + e);
            dbVersion = 0;
            Connection conn = JavaCIPUnknownScope.getConnection();
            try {
                if (!conn.getAutoCommit()) {
                    conn.rollback();
                }
                conn.setAutoCommit(false);
            } catch (SQLRuntimeException e1) {
                throw new MigrationRuntimeException("Could not reset transaction state", e1);
            }
        }
        return dbVersion;
    }
}
