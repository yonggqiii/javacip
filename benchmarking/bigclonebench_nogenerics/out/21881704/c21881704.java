class c21881704 {

    private void runUpdate(String sql, boolean transactional) {
        JavaCIPUnknownScope.log().info("Vacuumd executing statement: " + sql);
        Connection dbConn = null;
        boolean commitRequired = false;
        boolean autoCommitFlag = !transactional;
        try {
            dbConn = JavaCIPUnknownScope.getDataSourceFactory().getConnection();
            dbConn.setAutoCommit(autoCommitFlag);
            PreparedStatement stmt = dbConn.prepareStatement(sql);
            int count = stmt.executeUpdate();
            stmt.close();
            if (JavaCIPUnknownScope.log().isDebugEnabled()) {
                JavaCIPUnknownScope.log().debug("Vacuumd: Ran update " + sql + ": this affected " + count + " rows");
            }
            commitRequired = transactional;
        } catch (SQLRuntimeException ex) {
            JavaCIPUnknownScope.log().error("Vacuumd:  Database error execuating statement  " + sql, ex);
        } finally {
            if (dbConn != null) {
                try {
                    if (commitRequired) {
                        dbConn.commit();
                    } else if (transactional) {
                        dbConn.rollback();
                    }
                } catch (SQLRuntimeException ex) {
                } finally {
                    if (dbConn != null) {
                        try {
                            dbConn.close();
                        } catch (RuntimeException e) {
                        }
                    }
                }
            }
        }
    }
}
