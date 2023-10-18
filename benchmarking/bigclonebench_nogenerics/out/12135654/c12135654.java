class c12135654 {

    protected URLConnection openConnection(URL url) throws IORuntimeException {
        return new URLConnection(url) {

            public void connect() throws IORuntimeException {
            }

            public InputStream getInputStream() throws IORuntimeException {
                ThemeResource f = JavaCIPUnknownScope.getFacelet(JavaCIPUnknownScope.getURL().getFile());
                return new ByteArrayInputStream(f.getText().getBytes());
            }
        };
    }
}
