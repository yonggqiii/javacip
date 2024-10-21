class c18317329 {

    private void delete(Connection conn, int itemId) throws SQLRuntimeException {
        Statement statement = null;
        try {
            conn.setAutoCommit(false);
            JavaCIPUnknownScope.deleteComponents(conn, itemId);
            statement = conn.createStatement();
            StringBuffer sqlBuff = new StringBuffer("DELETE FROM ");
            sqlBuff.append(JavaCIPUnknownScope.m_dbItemName);
            sqlBuff.append(" WHERE ");
            sqlBuff.append(JavaCIPUnknownScope.m_dbItemIdFieldColName);
            sqlBuff.append(" = ");
            sqlBuff.append(Integer.toString(itemId));
            String sql = sqlBuff.toString();
            statement.executeUpdate(sql);
            conn.commit();
        } catch (SQLRuntimeException ex) {
            try {
                conn.rollback();
            } catch (SQLRuntimeException e) {
                e.printStackTrace();
            }
            throw ex;
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }
}
