


class c20640355 {

    public void downloadFile(OutputStream os, int fileId) throws IORuntimeException, SQLRuntimeException {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            Guard.checkConnectionNotNull(conn);
            PreparedStatement ps = conn.prepareStatement("select * from FILE_BODIES where file_id=?");
            ps.setInt(1, fileId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new FileNotFoundRuntimeException("File with id=" + fileId + " not found!");
            }
            Blob blob = rs.getBlob("data");
            InputStream is = blob.getBinaryStream();
            IOUtils.copyLarge(is, os);
        } finally {
            JdbcDaoHelper.safeClose(conn, log);
        }
    }

}
