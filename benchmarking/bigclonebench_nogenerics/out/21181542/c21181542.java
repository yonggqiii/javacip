class c21181542 {

    public boolean deleteRoleType(int id, int namespaceId, boolean removeReferencesInRoleTypes, DTSPermission permit) throws SQLRuntimeException, PermissionRuntimeException, DTSValidationRuntimeException {
        JavaCIPUnknownScope.checkPermission(permit, String.valueOf(namespaceId));
        boolean exist = JavaCIPUnknownScope.isRoleTypeUsed(namespaceId, id);
        if (exist) {
            throw new DTSValidationRuntimeException(ApelMsgHandler.getInstance().getMsg("DTS-0034"));
        }
        if (!removeReferencesInRoleTypes) {
            StringBuffer msgBuf = new StringBuffer();
            DTSTransferObject[] objects = JavaCIPUnknownScope.fetchRightIdentityReferences(namespaceId, id);
            if (objects.length > 0) {
                msgBuf.append("Role Type is Right Identity in one or more Role Types.");
            }
            objects = JavaCIPUnknownScope.fetchParentReferences(namespaceId, id);
            if (objects.length > 0) {
                if (msgBuf.length() > 0) {
                    msgBuf.append("\n");
                }
                msgBuf.append("Role Type is Parent of one or more Role Types.");
            }
            if (msgBuf.length() > 0) {
                throw new DTSValidationRuntimeException(msgBuf.toString());
            }
        }
        String sqlRightId = JavaCIPUnknownScope.getDAO().getStatement(JavaCIPUnknownScope.ROLE_TYPE_TABLE_KEY, "DELETE_RIGHT_IDENTITY_REF");
        String sqlParent = JavaCIPUnknownScope.getDAO().getStatement(JavaCIPUnknownScope.ROLE_TYPE_TABLE_KEY, "DELETE_PARENT_REF");
        String sql = JavaCIPUnknownScope.getDAO().getStatement(JavaCIPUnknownScope.ROLE_TYPE_TABLE_KEY, "DELETE");
        PreparedStatement pstmt = null;
        boolean success = false;
        long typeGid = JavaCIPUnknownScope.getGID(namespaceId, id);
        JavaCIPUnknownScope.conn.setAutoCommit(false);
        int defaultLevel = JavaCIPUnknownScope.conn.getTransactionIsolation();
        JavaCIPUnknownScope.conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        try {
            pstmt = JavaCIPUnknownScope.conn.prepareStatement(sqlRightId);
            pstmt.setLong(1, typeGid);
            pstmt.executeUpdate();
            pstmt.close();
            pstmt = JavaCIPUnknownScope.conn.prepareStatement(sqlParent);
            pstmt.setLong(1, typeGid);
            pstmt.executeUpdate();
            pstmt.close();
            pstmt = JavaCIPUnknownScope.conn.prepareStatement(sql);
            pstmt.setLong(1, typeGid);
            int count = pstmt.executeUpdate();
            success = (count == 1);
            JavaCIPUnknownScope.conn.commit();
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.conn.rollback();
            throw e;
        } finally {
            JavaCIPUnknownScope.conn.setTransactionIsolation(defaultLevel);
            JavaCIPUnknownScope.conn.setAutoCommit(true);
            JavaCIPUnknownScope.closeStatement(pstmt);
        }
        return success;
    }
}
