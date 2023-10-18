class c1141361 {

    public void makeRead(final String user, final long databaseID, final long time) throws SQLRuntimeException {
        final String query = "insert into fs.read_post (post, user, read_date) values (?, ?, ?)";
        JavaCIPUnknownScope.ensureConnection();
        final PreparedStatement statement = JavaCIPUnknownScope.m_connection.prepareStatement(query);
        try {
            statement.setLong(1, databaseID);
            statement.setString(2, user);
            statement.setTimestamp(3, new Timestamp(time));
            final int count = statement.executeUpdate();
            if (0 == count) {
                throw new SQLRuntimeException("Nothing updated.");
            }
            JavaCIPUnknownScope.m_connection.commit();
        } catch (final SQLRuntimeException e) {
            JavaCIPUnknownScope.m_connection.rollback();
            throw e;
        } finally {
            statement.close();
        }
    }
}
