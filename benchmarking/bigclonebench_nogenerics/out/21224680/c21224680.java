class c21224680 {

    public void add(String user, String pass, boolean admin, boolean developer) throws FidoDatabaseRuntimeException {
        try {
            Connection conn = null;
            Statement stmt = null;
            try {
                conn = FidoDataSource.getConnection();
                conn.setAutoCommit(false);
                stmt = conn.createStatement();
                String sql;
                if (JavaCIPUnknownScope.contains(stmt, user) == true) {
                    sql = "update Principals set Password = '" + pass + "' " + " where PrincipalId = '" + user + "'";
                } else {
                    sql = "insert into Principals (PrincipalId, Password) " + " values ('" + user + "', '" + pass + "')";
                }
                stmt.executeUpdate(sql);
                JavaCIPUnknownScope.updateRoles(stmt, user, admin, developer);
                conn.commit();
            } catch (SQLRuntimeException e) {
                if (conn != null)
                    conn.rollback();
                throw e;
            } finally {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
        } catch (SQLRuntimeException e) {
            throw new FidoDatabaseRuntimeException(e);
        }
    }
}
