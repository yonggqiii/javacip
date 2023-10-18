


class c16511008 {

    public ProgramProfilingMessageSymbol createNewProfilingMessageSymbol(int configID, int programMessageSymbolID) throws AdaptationRuntimeException {
        ProgramProfilingMessageSymbol profilingMessageSymbol = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO ProgramProfilingMessageSymbols" + "(projectDeploymentConfigurationID, programMessageSymbolID)" + " VALUES (" + configID + ", " + programMessageSymbolID + ")";
            connection = DriverManager.getConnection(CONN_STR);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            query = "SELECT * FROM ProgramProfilingMessageSymbols WHERE " + "projectDeploymentConfigurationID = " + configID + " AND " + "programMessageSymbolID           = " + programMessageSymbolID;
            resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                connection.rollback();
                String msg = "Attempt to create program profiling message " + "symbol failed.";
                log.error(msg);
                throw new AdaptationRuntimeException(msg);
            }
            profilingMessageSymbol = getProfilingMessageSymbol(resultSet);
            connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                connection.rollback();
            } catch (RuntimeException e) {
            }
            String msg = "SQLRuntimeException in createNewProfilingMessageSymbol";
            log.error(msg, ex);
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
        return profilingMessageSymbol;
    }

}
