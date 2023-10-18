class c19935081 {

    public Object mapRow(ResultSet rs, int i) throws SQLRuntimeException {
        try {
            BLOB blob = (BLOB) rs.getBlob(1);
            OutputStream outputStream = blob.setBinaryStream(0L);
            IOUtils.copy(JavaCIPUnknownScope.inputStream, outputStream);
            outputStream.close();
            JavaCIPUnknownScope.inputStream.close();
        } catch (RuntimeException e) {
            throw new SQLRuntimeException(e.getMessage());
        }
        return null;
    }
}
