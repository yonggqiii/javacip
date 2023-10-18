class c3026763 {

    public MoteDeploymentConfiguration updateMoteDeploymentConfiguration(int mdConfigID, int programID, int radioPowerLevel) throws AdaptationRuntimeException {
        MoteDeploymentConfiguration mdc = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "UPDATE MoteDeploymentConfigurations SET " + "programID       = " + programID + ", " + "radioPowerLevel = " + radioPowerLevel + "  " + "WHERE id = " + mdConfigID;
            connection = DriverManager.getConnection(JavaCIPUnknownScope.CONN_STR);
            statement = connection.createStatement();
            statement.executeUpdate(query);
            query = "SELECT * from MoteDeploymentConfigurations WHERE " + "id = " + mdConfigID;
            resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                connection.rollback();
                String msg = "Unable to select updated config.";
                JavaCIPUnknownScope.log.error(msg);
                ;
                throw new AdaptationRuntimeException(msg);
            }
            mdc = JavaCIPUnknownScope.getMoteDeploymentConfiguration(resultSet);
            connection.commit();
        } catch (SQLRuntimeException ex) {
            try {
                connection.rollback();
            } catch (RuntimeException e) {
            }
            String msg = "SQLRuntimeException in updateMoteDeploymentConfiguration";
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
        return mdc;
    }
}
