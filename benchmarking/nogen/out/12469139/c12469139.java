class c12469139 {

    public Program createNewProgram(int projectID, String name, String description) throws AdaptationRuntimeException {
        Program program = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(JavaCIPUnknownScope.CONN_STR);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String query = "INSERT INTO Programs(projectID, name, " + "description, sourcePath) VALUES ( " + projectID + ", " + "'" + name + "', " + "'" + description + "', " + "'" + "[unknown]" + "')";
            JavaCIPUnknownScope.log.debug("SQL Query:\n" + query);
            statement.executeUpdate(query);
            query = "SELECT * FROM Programs WHERE " + " projectID   =  " + projectID + "  AND " + " name        = '" + name + "' AND " + " description = '" + description + "'";
            resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                connection.rollback();
                String msg = "Attempt to create program failed";
                JavaCIPUnknownScope.log.error(msg);
                throw new AdaptationRuntimeException(msg);
            }
            program = JavaCIPUnknownScope.getProgram(resultSet);
            connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                connection.rollback();
            } catch (RuntimeException e) {
            }
            String msg = "SQLRuntimeException in createNewProgram";
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
        return program;
    }
}
