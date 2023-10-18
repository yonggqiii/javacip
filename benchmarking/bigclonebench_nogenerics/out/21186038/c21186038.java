class c21186038 {

    public Project deleteProject(int projectID) throws AdaptationRuntimeException {
        Project project = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM Projects WHERE id = " + projectID;
            connection = DriverManager.getConnection(JavaCIPUnknownScope.CONN_STR);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                connection.rollback();
                String msg = "Attempt to delete project failed.";
                JavaCIPUnknownScope.log.error(msg);
                throw new AdaptationRuntimeException(msg);
            }
            project = JavaCIPUnknownScope.getProject(resultSet);
            query = "DELETE FROM Projects WHERE id = " + projectID;
            statement.executeUpdate(query);
            connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                connection.rollback();
            } catch (RuntimeException e) {
            }
            String msg = "SQLRuntimeException in deleteProject";
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
