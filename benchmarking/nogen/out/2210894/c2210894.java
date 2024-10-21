class c2210894 {

    protected int doExecuteInsert(PreparedStatement statement, Table data) throws SQLRuntimeException {
        ResultSet rs = null;
        int result = -1;
        try {
            JavaCIPUnknownScope.lastError = null;
            result = statement.executeUpdate();
            if (!JavaCIPUnknownScope.isAutoCommit())
                JavaCIPUnknownScope.connection.commit();
            rs = statement.getGeneratedKeys();
            while (rs.next()) {
                FieldUtils.setValue(data, data.key, rs.getObject(1));
            }
        } catch (SQLRuntimeException ex) {
            if (!JavaCIPUnknownScope.isAutoCommit()) {
                JavaCIPUnknownScope.lastError = ex;
                JavaCIPUnknownScope.connection.rollback();
                LogUtils.log(Level.SEVERE, "Transaction is being rollback. Error: " + ex.toString());
            } else {
                throw ex;
            }
        } finally {
            if (statement != null)
                statement.close();
            if (rs != null)
                rs.close();
        }
        return result;
    }
}
