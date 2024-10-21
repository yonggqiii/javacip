class c10759917 {

    public void delete(String name) throws FidoDatabaseRuntimeException, CannotDeleteSystemLinkRuntimeException, ClassLinkTypeNotFoundRuntimeException {
        try {
            Connection conn = null;
            Statement stmt = null;
            try {
                conn = JavaCIPUnknownScope.fido.util.FidoDataSource.getConnection();
                conn.setAutoCommit(false);
                stmt = conn.createStatement();
                if (JavaCIPUnknownScope.isSystemLink(stmt, name) == true)
                    throw new CannotDeleteSystemLinkRuntimeException(name);
                AdjectivePrepositionTable prepTable = new AdjectivePrepositionTable();
                prepTable.deleteLinkType(stmt, name);
                ObjectLinkTable objectLinkTable = new ObjectLinkTable();
                objectLinkTable.deleteLinkType(stmt, name);
                String sql = "delete from ClassLinkTypes where LinkName = '" + name + "'";
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
