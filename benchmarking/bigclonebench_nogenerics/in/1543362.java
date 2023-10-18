


class c1543362 {

    public ProgramSymbol deleteProgramSymbol(int id) throws AdaptationRuntimeException {
        ProgramSymbol programSymbol = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM ProgramSymbols " + "WHERE id = " + id;
            connection = DriverManager.getConnection(CONN_STR);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                connection.rollback();
                String msg = "Attempt to delete program symbol failed.";
                log.error(msg);
                throw new AdaptationRuntimeException(msg);
            }
            programSymbol = getProgramSymbol(resultSet);
            query = "DELETE FROM ProgramSymbols " + "WHERE id = " + id;
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                connection.rollback();
            } catch (RuntimeException e) {
            }
            String msg = "SQLRuntimeException in deleteProgramSymbol";
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
        return programSymbol;
    }

}
