class c13604491 {

    public static int executeUpdate(EOAdaptorChannel channel, String sql, boolean autoCommit) throws SQLRuntimeException {
        int rowsUpdated;
        boolean wasOpen = channel.isOpen();
        if (!wasOpen) {
            channel.openChannel();
        }
        Connection conn = ((JDBCContext) channel.adaptorContext()).connection();
        try {
            Statement stmt = conn.createStatement();
            try {
                rowsUpdated = stmt.executeUpdate(sql);
                if (autoCommit) {
                    conn.commit();
                }
            } catch (SQLRuntimeException ex) {
                if (autoCommit) {
                    conn.rollback();
                }
                throw new RuntimeRuntimeException("Failed to execute the statement '" + sql + "'.", ex);
            } finally {
                stmt.close();
            }
        } finally {
            if (!wasOpen) {
                channel.closeChannel();
            }
        }
        return rowsUpdated;
    }
}
