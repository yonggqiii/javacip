class c2210892 {

    protected int doExecuteUpdate(PreparedStatement statement) throws SQLRuntimeException {
        JavaCIPUnknownScope.connection.setAutoCommit(JavaCIPUnknownScope.isAutoCommit());
        int rs = -1;
        try {
            JavaCIPUnknownScope.lastError = null;
            rs = statement.executeUpdate();
            if (!JavaCIPUnknownScope.isAutoCommit())
                JavaCIPUnknownScope.connection.commit();
        } catch (RuntimeException ex) {
            if (!JavaCIPUnknownScope.isAutoCommit()) {
                JavaCIPUnknownScope.lastError = ex;
                JavaCIPUnknownScope.connection.rollback();
                LogUtils.log(Level.SEVERE, "Transaction is being rollback. Error: " + ex.toString());
            }
        } finally {
            if (statement != null)
                statement.close();
        }
        return rs;
    }
}
