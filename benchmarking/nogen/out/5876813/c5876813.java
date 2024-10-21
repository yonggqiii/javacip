class c5876813 {

    public HttpResponse execute() throws IORuntimeException {
        return new HttpResponse() {

            public int getResponseCode() throws IORuntimeException {
                return JavaCIPUnknownScope.conn.getResponseCode();
            }

            public InputStream getContentStream() throws IORuntimeException {
                InputStream errStream = JavaCIPUnknownScope.conn.getErrorStream();
                if (errStream != null)
                    return errStream;
                else
                    return JavaCIPUnknownScope.conn.getInputStream();
            }
        };
    }
}
