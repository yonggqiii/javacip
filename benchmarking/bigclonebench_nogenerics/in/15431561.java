


class c15431561 {

    public ProgramProfilingSymbol updateProgramProfilingSymbol(int id, int configID, int programSymbolID) throws AdaptationRuntimeException {
        ProgramProfilingSymbol pps = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "UPDATE ProgramProfilingSymbols SET " + "projectDeploymentConfigurationID = " + configID + ", " + "programSymbolID                  = " + programSymbolID + ", " + "WHERE id = " + id;
            connection = DriverManager.getConnection(CONN_STR);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            query = "SELECT * from ProgramProfilingSymbols WHERE " + "id = " + id;
            resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                connection.rollback();
                String msg = "Attempt to update program profiling " + "symbol failed.";
                log.error(msg);
                throw new AdaptationRuntimeException(msg);
            }
            pps = getProfilingSymbol(resultSet);
            connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                connection.rollback();
            } catch (RuntimeException e) {
            }
            String msg = "SQLRuntimeException in updateProgramProfilingSymbol";
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
        return pps;
    }

}
