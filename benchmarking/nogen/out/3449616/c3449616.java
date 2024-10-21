class c3449616 {

    public ProgramMessageSymbol deleteProgramMessageSymbol(int id) throws AdaptationRuntimeException {
        ProgramMessageSymbol pmt = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM ProgramMessageSymbols " + "WHERE id = " + id;
            connection = DriverManager.getConnection(JavaCIPUnknownScope.CONN_STR);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                String msg = "Attempt to delete program message type " + "failed.";
                JavaCIPUnknownScope.log.error(msg);
                ;
                throw new AdaptationRuntimeException(msg);
            }
            pmt = JavaCIPUnknownScope.getProgramMessageSymbol(resultSet);
            query = "DELETE FROM ProgramMessageSymbols " + "WHERE id = " + id;
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                connection.rollback();
            } catch (RuntimeException e) {
            }
            String msg = "SQLRuntimeException in deleteProgramMessageSymbol";
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
        return pmt;
    }
}
