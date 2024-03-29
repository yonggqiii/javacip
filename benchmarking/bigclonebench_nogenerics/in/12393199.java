


class c12393199 {

    public int getDBVersion() throws MigrationRuntimeException {
        int dbVersion;
        PreparedStatement ps;
        try {
            Connection conn = getConnection();
            ps = conn.prepareStatement("SELECT version FROM " + getTablename());
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    if (rs.next()) {
                        dbVersion = rs.getInt(1);
                        if (rs.next()) {
                            throw new MigrationRuntimeException("Too many version in table: " + getTablename());
                        }
                    } else {
                        ps.close();
                        ps = conn.prepareStatement("INSERT INTO " + getTablename() + " (version) VALUES (?)");
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
            logger.log(Level.WARNING, "Could not access " + tablename + ": " + e);
            dbVersion = 0;
            Connection conn = getConnection();
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
