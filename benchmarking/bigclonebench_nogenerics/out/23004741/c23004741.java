class c23004741 {

    private boolean delete() {
        boolean ret = false;
        try {
            Connection conn = ((JdbcRequestCycle) JavaCIPUnknownScope.getRequestCycle()).getConnection();
            if (conn == null) {
                throw new RestartResponseRuntimeException(new OkErrorPage(OkErrorEnum.DATABASE));
            }
            String query = "delete from revisions where entry=?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, JavaCIPUnknownScope.entry);
            int revisionsRowsAffected = pstmt.executeUpdate();
            query = "delete from entry where id=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, JavaCIPUnknownScope.entry);
            int entryRowAffected = pstmt.executeUpdate();
            if (entryRowAffected > 0) {
                ret = true;
            } else {
                conn.rollback();
            }
            JavaCIPUnknownScope.info(entryRowAffected + " entry with " + revisionsRowsAffected + " revisions was deleted.");
        } catch (SQLRuntimeException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
}
