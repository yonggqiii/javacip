


class c7805762 {

    public Object mapRow(ResultSet rs, int i) throws SQLRuntimeException {
        Blob blob = rs.getBlob(1);
        if (rs.wasNull()) return null;
        try {
            InputStream inputStream = blob.getBinaryStream();
            if (length > 0) IOUtils.copy(inputStream, outputStream, offset, length); else IOUtils.copy(inputStream, outputStream);
            inputStream.close();
        } catch (IORuntimeException e) {
        }
        return null;
    }

}
