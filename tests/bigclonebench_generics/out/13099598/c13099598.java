class c13099598 {

    public void deleteUser(String userID) throws XregistryException {
        try {
            userID = Utils.canonicalizeDN(userID);
            Connection connection = JavaCIPUnknownScope.context.createConnection();
            connection.setAutoCommit(false);
            try {
                PreparedStatement statement1 = connection.prepareStatement(JavaCIPUnknownScope.DELETE_USER_SQL_MAIN);
                statement1.setString(1, userID);
                statement1.executeUpdate();
                PreparedStatement statement2 = connection.prepareStatement(JavaCIPUnknownScope.DELETE_USER_SQL_DEPEND);
                statement2.setString(1, userID);
                statement2.executeUpdate();
                connection.commit();
                Collection<Group> groupList = JavaCIPUnknownScope.groups.values();
                for (Group group : groupList) {
                    group.removeUser(userID);
                }
                JavaCIPUnknownScope.log.info("Delete User " + userID);
            } catch (SQLException e) {
                connection.rollback();
                throw new XregistryException(e);
            } finally {
                JavaCIPUnknownScope.context.closeConnection(connection);
            }
        } catch (SQLException e) {
            throw new XregistryException(e);
        }
    }
}
