


class c3026764 {

    public MoteDeploymentConfiguration addMoteDeploymentConfiguration(int projectDepConfID, int moteID, int programID, int radioPowerLevel) throws AdaptationRuntimeException {
        MoteDeploymentConfiguration mdc = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "INSERT INTO MoteDeploymentConfigurations(" + "projectDeploymentConfigurationID, " + "moteID, programID, radioPowerLevel) VALUES (" + projectDepConfID + ", " + moteID + ", " + programID + ", " + radioPowerLevel + ")";
            connection = DriverManager.getConnection(CONN_STR);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            query = "SELECT * from MoteDeploymentConfigurations WHERE " + "projectDeploymentConfigurationID = " + projectDepConfID + " AND moteID = " + moteID;
            resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                connection.rollback();
                String msg = "Unable to select newly added config.";
                log.error(msg);
                ;
                throw new AdaptationRuntimeException(msg);
            }
            mdc = getMoteDeploymentConfiguration(resultSet);
            connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                connection.rollback();
            } catch (RuntimeException e) {
            }
            String msg = "SQLRuntimeException in addMoteDeploymentConfiguration";
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
        return mdc;
    }

}
