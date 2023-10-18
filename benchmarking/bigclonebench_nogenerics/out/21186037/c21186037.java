class c21186037 {

    public Project createProject(int testbedID, String name, String description) throws AdaptationRuntimeException {
        Project project = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO Projects(testbedID, name, " + "description) VALUES (" + testbedID + ", '" + name + "', '" + description + "')";
            connection = DriverManager.getConnection(JavaCIPUnknownScope.CONN_STR);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            query = "SELECT * FROM Projects WHERE " + " testbedID   = " + testbedID + "  AND " + " name        = '" + name + "' AND " + " description = '" + description + "'";
            resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                connection.rollback();
                String msg = "Attempt to create project failed.";
                JavaCIPUnknownScope.log.error(msg);
                throw new AdaptationRuntimeException(msg);
            }
            project = JavaCIPUnknownScope.getProject(resultSet);
            connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                connection.rollback();
            } catch (RuntimeException e) {
            }
            String msg = "SQLRuntimeException in createProject";
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
        return project;
    }
}
