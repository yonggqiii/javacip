class c14050308 {

    public void deleteInstance(int instanceId) throws FidoDatabaseRuntimeException, ObjectNotFoundRuntimeException {
        try {
            Connection conn = null;
            Statement stmt = null;
            try {
                conn = JavaCIPUnknownScope.fido.util.FidoDataSource.getConnection();
                conn.setAutoCommit(false);
                stmt = conn.createStatement();
                if (JavaCIPUnknownScope.contains(stmt, instanceId) == false)
                    throw new ObjectNotFoundRuntimeException(instanceId);
                ObjectLinkTable objectLinkList = new ObjectLinkTable();
                ObjectAttributeTable objectAttributeList = new ObjectAttributeTable();
                objectLinkList.deleteObject(stmt, instanceId);
                objectAttributeList.deleteObject(stmt, instanceId);
                stmt.executeUpdate("delete from Objects where ObjectId = " + instanceId);
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
