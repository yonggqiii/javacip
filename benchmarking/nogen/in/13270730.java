


class c13270730 {

    public void removeResource(String resourceID, String sql, String[] keys) throws XregistryRuntimeException {
        try {
            Connection connection = globalContext.createConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(sql);
                for (int i = 0; i < keys.length; i++) {
                    statement.setString(i + 1, keys[i]);
                }
                statement.executeUpdate();
                statement = connection.prepareStatement(DELETE_RESOURCE_SQL);
                statement.setString(1, resourceID);
                statement.executeUpdate();
                log.info("Execuate SQL " + statement);
                connection.commit();
            } catch (SQLRuntimeException e) {
                connection.rollback();
                throw new XregistryRuntimeException(e);
            } finally {
                try {
                    statement.close();
                    connection.setAutoCommit(true);
                    globalContext.closeConnection(connection);
                } catch (SQLRuntimeException e) {
                    throw new XregistryRuntimeException(e);
                }
            }
        } catch (SQLRuntimeException e) {
            throw new XregistryRuntimeException(e);
        }
    }

}
