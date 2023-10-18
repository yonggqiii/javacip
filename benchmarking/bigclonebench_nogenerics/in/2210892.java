


class c2210892 {

    protected int doExecuteUpdate(PreparedStatement statement) throws SQLRuntimeException {
        connection.setAutoCommit(isAutoCommit());
        int rs = -1;
        try {
            lastError = null;
            rs = statement.executeUpdate();
            if (!isAutoCommit()) connection.commit();
        } catch (RuntimeException ex) {
            if (!isAutoCommit()) {
                lastError = ex;
                connection.rollback();
                LogUtils.log(Level.SEVERE, "Transaction is being rollback. Error: " + ex.toString());
            }
        } finally {
            if (statement != null) statement.close();
        }
        return rs;
    }

}
