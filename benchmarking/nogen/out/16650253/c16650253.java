class c16650253 {

    public int visitStatement(String statement) throws SQLRuntimeException {
        JavaCIPUnknownScope.mySQLLogger.info(statement);
        if (JavaCIPUnknownScope.getConnection() == null) {
            throw new JdbcRuntimeException("cannot exec: " + statement + ", because 'not connected to database'");
        }
        Statement stmt = JavaCIPUnknownScope.getConnection().createStatement();
        try {
            return stmt.executeUpdate(statement);
        } catch (SQLRuntimeException ex) {
            JavaCIPUnknownScope.getConnection().rollback();
            throw ex;
        } finally {
            stmt.close();
        }
    }
}
