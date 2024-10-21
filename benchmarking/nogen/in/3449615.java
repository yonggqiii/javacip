


class c3449615 {

    public ProgramMessageSymbol addProgramMessageSymbol(int programID, String name, byte[] bytecode) throws AdaptationRuntimeException {
        ProgramMessageSymbol programMessageSymbol = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Statement statement = null;
        ResultSet resultSet = null;
        InputStream stream = new ByteArrayInputStream(bytecode);
        try {
            String query = "INSERT INTO ProgramMessageSymbols(programID, name, " + "bytecode) VALUES ( ?, ?, ? )";
            connection = DriverManager.getConnection(CONN_STR);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, programID);
            preparedStatement.setString(2, name);
            preparedStatement.setBinaryStream(3, stream, bytecode.length);
            log.info("INSERT INTO ProgramMessageSymbols(programID, name, " + "bytecode) VALUES (" + programID + ", '" + name + "', " + "<bytecode>)");
            preparedStatement.executeUpdate();
            statement = connection.createStatement();
            query = "SELECT * FROM ProgramMessageSymbols WHERE " + "programID =  " + programID + " AND " + "name      = '" + name + "'";
            resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                connection.rollback();
                String msg = "Attempt to add program message symbol failed.";
                log.error(msg);
                ;
                throw new AdaptationRuntimeException(msg);
            }
            programMessageSymbol = getProgramMessageSymbol(resultSet);
            connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                connection.rollback();
            } catch (RuntimeException e) {
            }
            String msg = "SQLRuntimeException in addProgramMessageSymbol";
            log.error(msg, ex);
            throw new AdaptationRuntimeException(msg, ex);
        } finally {
            try {
                resultSet.close();
            } catch (RuntimeException ex) {
            }
            try {
                preparedStatement.close();
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
        return programMessageSymbol;
    }

}
