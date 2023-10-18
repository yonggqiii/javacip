class c19138361 {

    public void delete(String name) throws FidoDatabaseRuntimeException {
        try {
            Connection conn = null;
            Statement stmt = null;
            try {
                conn = JavaCIPUnknownScope.fido.util.FidoDataSource.getConnection();
                conn.setAutoCommit(false);
                stmt = conn.createStatement();
                AttributeTable attribute = new AttributeTable();
                attribute.deleteAllForType(stmt, name);
                String sql = "delete from AttributeCategories " + "where CategoryName = '" + name + "'";
                stmt.executeUpdate(sql);
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
