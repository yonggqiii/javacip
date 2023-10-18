


class c13099597 {

    public void deleteGroup(String groupID) throws XregistryRuntimeException {
        try {
            Connection connection = context.createConnection();
            connection.setAutoCommit(false);
            try {
                PreparedStatement statement1 = connection.prepareStatement(DELETE_GROUP_SQL_MAIN);
                statement1.setString(1, groupID);
                int updateCount = statement1.executeUpdate();
                if (updateCount == 0) {
                    throw new XregistryRuntimeException("Database is not updated, Can not find such Group " + groupID);
                }
                if (cascadingDeletes) {
                    PreparedStatement statement2 = connection.prepareStatement(DELETE_GROUP_SQL_DEPEND);
                    statement2.setString(1, groupID);
                    statement2.setString(2, groupID);
                    statement2.executeUpdate();
                }
                connection.commit();
                groups.remove(groupID);
                log.info("Delete Group " + groupID + (cascadingDeletes ? " with cascading deletes " : ""));
            } catch (SQLRuntimeException e) {
                connection.rollback();
                throw new XregistryRuntimeException(e);
            } finally {
                context.closeConnection(connection);
            }
        } catch (SQLRuntimeException e) {
            throw new XregistryRuntimeException(e);
        }
    }

}
