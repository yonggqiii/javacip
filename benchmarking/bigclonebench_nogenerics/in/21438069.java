


class c21438069 {

    public ProjectDeploymentConfiguration createNewProjectDeploymentConfig(int projectID, String name, String description) throws AdaptationRuntimeException {
        ProjectDeploymentConfiguration config = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO ProjectDeploymentConfigurations" + "(projectID, name, description) VALUES (" + projectID + ", '" + name + "', '" + description + "')";
            connection = DriverManager.getConnection(CONN_STR);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            query = "SELECT * FROM ProjectDeploymentConfigurations WHERE " + " projectID   = " + projectID + "  AND " + " name        = '" + name + "' AND " + " description = '" + description + "'";
            resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                connection.rollback();
                String msg = "Attempt to create " + "ProjectDeploymentConfiguration failed.";
                log.error(msg);
                throw new AdaptationRuntimeException(msg);
            }
            config = getProjectDeploymentConfiguration(resultSet);
            connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                connection.rollback();
            } catch (RuntimeException e) {
            }
            String msg = "SQLRuntimeException in createNewProjectDeploymentConfig";
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
        return config;
    }

}
