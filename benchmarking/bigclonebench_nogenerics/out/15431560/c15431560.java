class c15431560 {

    public ProgramProfilingSymbol deleteProfilingSymbol(int id) throws AdaptationRuntimeException {
        ProgramProfilingSymbol profilingSymbol = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM ProgramProfilingSymbols " + "WHERE id = " + id;
            connection = DriverManager.getConnection(JavaCIPUnknownScope.CONN_STR);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                connection.rollback();
                String msg = "Attempt to delete program profiling " + "symbol failed.";
                JavaCIPUnknownScope.log.error(msg);
                throw new AdaptationRuntimeException(msg);
            }
            profilingSymbol = JavaCIPUnknownScope.getProfilingSymbol(resultSet);
            query = "DELETE FROM ProgramProfilingSymbols " + "WHERE id = " + id;
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                connection.rollback();
            } catch (RuntimeException e) {
            }
            String msg = "SQLRuntimeException in deleteProfilingSymbol";
            JavaCIPUnknownScope.log.error(msg, ex);
            throw new AdaptationRuntimeException(msg, ex);
        } finally {
            try {
                resultSet.close();
            } catch (RuntimeException ex) {
            }
            try {
                statement.close();
            } catch (RuntimeException ex) {
            }
            try {
                connection.close();
            } catch (RuntimeException ex) {
            }
        }
        return profilingSymbol;
    }
}
