class c11422574 {

    protected boolean update(String sql, int requiredRows, int maxRows) throws SQLRuntimeException {
        if (JavaCIPUnknownScope.LOG.isDebugEnabled()) {
            JavaCIPUnknownScope.LOG.debug("executing " + sql + "...");
        }
        Connection connection = null;
        boolean oldAutoCommit = true;
        try {
            connection = JavaCIPUnknownScope.dataSource.getConnection();
            connection.clearWarnings();
            oldAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(sql);
            if (requiredRows != -1 && rowsAffected < requiredRows) {
                JavaCIPUnknownScope.LOG.warn("(" + rowsAffected + ") less than " + requiredRows + " rows affected, rolling back...");
                connection.rollback();
                return false;
            }
            if (maxRows != -1 && rowsAffected > maxRows) {
                JavaCIPUnknownScope.LOG.warn("(" + rowsAffected + ") more than " + maxRows + " rows affected, rolling back...");
                connection.rollback();
                return false;
            }
            connection.commit();
            return true;
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.LOG.error("Unable to update database using: " + sql, e);
            throw e;
        } finally {
            try {
                if (connection != null) {
                    connection.setAutoCommit(oldAutoCommit);
                    connection.close();
                }
            } catch (SQLRuntimeException e) {
                JavaCIPUnknownScope.LOG.error("Unable to close connection: " + e, e);
            }
        }
    }
}
