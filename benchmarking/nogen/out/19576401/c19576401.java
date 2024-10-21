class c19576401 {

    public static final int executeSql(final Connection conn, final String sql, final boolean rollback) throws SQLRuntimeException {
        if (null == sql)
            return 0;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            final int updated = stmt.executeUpdate(sql);
            return updated;
        } catch (final SQLRuntimeException e) {
            if (rollback)
                conn.rollback();
            throw e;
        } finally {
            JavaCIPUnknownScope.closeAll(null, stmt, null);
        }
    }
}
