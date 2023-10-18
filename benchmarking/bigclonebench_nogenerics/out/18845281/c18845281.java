class c18845281 {

    public static void readFile(FOUserAgent ua, String uri, Writer output, String encoding) throws IORuntimeException {
        InputStream in = JavaCIPUnknownScope.getURLInputStream(ua, uri);
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(in, writer, encoding);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
