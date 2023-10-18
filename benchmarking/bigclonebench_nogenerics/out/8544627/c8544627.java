class c8544627 {

    protected void copyContent(String filename) throws IORuntimeException {
        InputStream in = null;
        try {
            in = JavaCIPUnknownScope.LOADER.getResourceAsStream(JavaCIPUnknownScope.RES_PKG + filename);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.copy(in, out);
            JavaCIPUnknownScope.setResponseData(out.toByteArray());
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }
}
