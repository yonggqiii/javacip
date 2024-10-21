class c7805762 {

    public Object mapRow(ResultSet rs, int i) throws SQLRuntimeException {
        Blob blob = rs.getBlob(1);
        if (rs.wasNull())
            return null;
        try {
            InputStream inputStream = blob.getBinaryStream();
            if (JavaCIPUnknownScope.length > 0)
                IOUtils.copy(inputStream, JavaCIPUnknownScope.outputStream, JavaCIPUnknownScope.offset, JavaCIPUnknownScope.length);
            else
                IOUtils.copy(inputStream, JavaCIPUnknownScope.outputStream);
            inputStream.close();
        } catch (IORuntimeException e) {
        }
        return null;
    }
}
