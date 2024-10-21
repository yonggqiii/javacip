


class c10281203 {

    public void makeRead(String user, long databaseID, long time) throws SQLRuntimeException {
        String query = "replace into fs.read_post (post, user, read_date) values (?, ?, ?)";
        ensureConnection();
        PreparedStatement statement = m_connection.prepareStatement(query);
        try {
            statement.setLong(1, databaseID);
            statement.setString(2, user);
            statement.setTimestamp(3, new Timestamp(time));
            int count = statement.executeUpdate();
            if (0 == count) throw new SQLRuntimeException("Nothing updated.");
            m_connection.commit();
        } catch (SQLRuntimeException e) {
            m_connection.rollback();
            throw e;
        } finally {
            statement.close();
        }
    }

}
