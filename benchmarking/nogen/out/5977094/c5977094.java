class c5977094 {

    public int deleteFile(Integer[] fileID) throws SQLRuntimeException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection conn = null;
        int nbrow = 0;
        try {
            DataSource ds = JavaCIPUnknownScope.getDataSource(JavaCIPUnknownScope.DEFAULT_DATASOURCE);
            conn = ds.getConnection();
            conn.setAutoCommit(false);
            if (JavaCIPUnknownScope.log.isDebugEnabled()) {
                JavaCIPUnknownScope.log.debug("FileDAOImpl.deleteFile() " + JavaCIPUnknownScope.DELETE_FILES_LOGIC);
            }
            for (int i = 0; i < fileID.length; i++) {
                pstmt = conn.prepareStatement(JavaCIPUnknownScope.DELETE_FILES_LOGIC);
                pstmt.setInt(1, fileID[i].intValue());
                nbrow = pstmt.executeUpdate();
            }
        } catch (SQLRuntimeException e) {
            conn.rollback();
            JavaCIPUnknownScope.log.error("FileDAOImpl.deleteFile() : erreur technique", e);
            throw e;
        } finally {
            conn.commit();
            JavaCIPUnknownScope.closeRessources(conn, pstmt, rs);
        }
        return nbrow;
    }
}
