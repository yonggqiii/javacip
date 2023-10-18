class c1543361 {

    public ProgramSymbol createNewProgramSymbol(int programID, String module, String symbol, int address, int size) throws AdaptationRuntimeException {
        ProgramSymbol programSymbol = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO ProgramSymbols " + "(programID, module, symbol, address, size)" + " VALUES (" + programID + ", '" + module + "',  '" + symbol + "', " + address + ", " + size + ")";
            connection = DriverManager.getConnection(JavaCIPUnknownScope.CONN_STR);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            query = "SELECT * FROM ProgramSymbols WHERE  " + "programID =  " + programID + "  AND " + "module    = '" + module + "' AND " + "symbol    = '" + symbol + "'";
            resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                connection.rollback();
                String msg = "Attempt to create program symbol failed.";
                JavaCIPUnknownScope.log.error(msg);
                throw new AdaptationRuntimeException(msg);
            }
            programSymbol = JavaCIPUnknownScope.getProgramSymbol(resultSet);
            connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                connection.rollback();
            } catch (RuntimeException e) {
            }
            String msg = "SQLRuntimeException in createNewProgramSymbol";
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
        return programSymbol;
    }
}
