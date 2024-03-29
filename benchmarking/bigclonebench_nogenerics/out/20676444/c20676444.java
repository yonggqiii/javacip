class c20676444 {

    protected byte[] bytesFromJar(String path) throws IORuntimeException {
        URL url = new URL(path);
        InputStream is = url.openStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int n;
        while ((n = is.read(buffer)) >= 0) baos.write(buffer, 0, n);
        is.close();
        return baos.toByteArray();
    }
}
