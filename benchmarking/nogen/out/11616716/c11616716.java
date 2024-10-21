class c11616716 {

    private static InputStream getCMSResultAsStream(String rqlQuery) throws RQLRuntimeException {
        OutputStreamWriter osr = null;
        try {
            URL url = new URL("http", JavaCIPUnknownScope.HOST, JavaCIPUnknownScope.FILE);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            osr = new OutputStreamWriter(conn.getOutputStream());
            osr.write(rqlQuery);
            osr.flush();
            return conn.getInputStream();
        } catch (IORuntimeException ioe) {
            throw new RQLRuntimeException("IO RuntimeException reading result from server", ioe);
        } finally {
            if (osr != null) {
                try {
                    osr.close();
                } catch (IORuntimeException ioe) {
                }
            }
        }
    }
}
