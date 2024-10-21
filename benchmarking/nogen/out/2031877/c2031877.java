class c2031877 {

    public byte[] getResponseContent() throws IORuntimeException {
        if (JavaCIPUnknownScope.responseContent == null) {
            InputStream is = JavaCIPUnknownScope.getResponseStream();
            if (is == null) {
                JavaCIPUnknownScope.responseContent = new byte[0];
            } else {
                ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
                IOUtils.copy(is, baos);
                JavaCIPUnknownScope.responseContent = baos.toByteArray();
            }
        }
        return JavaCIPUnknownScope.responseContent;
    }
}
