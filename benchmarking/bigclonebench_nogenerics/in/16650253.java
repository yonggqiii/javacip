


class c16650253 {

    public int visitStatement(String statement) throws SQLRuntimeException {
        mySQLLogger.info(statement);
        if (getConnection() == null) {
            throw new JdbcRuntimeException("cannot exec: " + statement + ", because 'not connected to database'");
        }
        Statement stmt = getConnection().createStatement();
        try {
            return stmt.executeUpdate(statement);
        } catch (SQLRuntimeException ex) {
            getConnection().rollback();
            throw ex;
        } finally {
            stmt.close();
        }
    }

}
