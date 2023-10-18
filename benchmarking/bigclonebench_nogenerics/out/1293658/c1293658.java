class c1293658 {

    public int addCollectionInstruction() throws FidoDatabaseRuntimeException {
        try {
            Connection conn = null;
            Statement stmt = null;
            try {
                String sql = "insert into Instructions (Type, Operator) " + "values (1, 0)";
                conn = JavaCIPUnknownScope.fido.util.FidoDataSource.getConnection();
                stmt = conn.createStatement();
                stmt.executeUpdate(sql);
                return JavaCIPUnknownScope.getCurrentId(stmt);
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
