class c13099597 {

    public void deleteGroup(String groupID) throws XregistryRuntimeException {
        try {
            Connection connection = JavaCIPUnknownScope.context.createConnection();
            connection.setAutoCommit(false);
            try {
                PreparedStatement statement1 = connection.prepareStatement(JavaCIPUnknownScope.DELETE_GROUP_SQL_MAIN);
                statement1.setString(1, groupID);
                int updateCount = statement1.executeUpdate();
                if (updateCount == 0) {
                    throw new XregistryRuntimeException("Database is not updated, Can not find such Group " + groupID);
                }
                if (JavaCIPUnknownScope.cascadingDeletes) {
                    PreparedStatement statement2 = connection.prepareStatement(JavaCIPUnknownScope.DELETE_GROUP_SQL_DEPEND);
                    statement2.setString(1, groupID);
                    statement2.setString(2, groupID);
                    statement2.executeUpdate();
                }
                connection.commit();
                JavaCIPUnknownScope.groups.remove(groupID);
                JavaCIPUnknownScope.log.info("Delete Group " + groupID + (JavaCIPUnknownScope.cascadingDeletes ? " with cascading deletes " : ""));
            } catch (SQLRuntimeException e) {
                connection.rollback();
                throw new XregistryRuntimeException(e);
            } finally {
                JavaCIPUnknownScope.context.closeConnection(connection);
            }
        } catch (SQLRuntimeException e) {
            throw new XregistryRuntimeException(e);
        }
    }
}
