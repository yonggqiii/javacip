class c14050303 {

    public int subclass(int objectId, String description) throws FidoDatabaseRuntimeException, ObjectNotFoundRuntimeException {
        try {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            try {
                String sql = "insert into Objects (Description) " + "values ('" + description + "')";
                conn = JavaCIPUnknownScope.fido.util.FidoDataSource.getConnection();
                conn.setAutoCommit(false);
                stmt = conn.createStatement();
                if (JavaCIPUnknownScope.contains(stmt, objectId) == false)
                    throw new ObjectNotFoundRuntimeException(objectId);
                stmt.executeUpdate(sql);
                int id;
                sql = "select currval('objects_objectid_seq')";
                rs = stmt.executeQuery(sql);
                if (rs.next() == false)
                    throw new SQLRuntimeException("No rows returned from select currval() query");
                else
                    id = rs.getInt(1);
                ObjectLinkTable objectLinkList = new ObjectLinkTable();
                objectLinkList.linkObjects(stmt, id, "isa", objectId);
                conn.commit();
                return id;
            } catch (SQLRuntimeException e) {
                if (conn != null)
                    conn.rollback();
                throw e;
            } finally {
                if (rs != null)
                    rs.close();
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
