class c19738435 {

    public Resource parse(URL url, IRDFContainer target) throws RDFRuntimeException, IORuntimeException {
        JavaCIPUnknownScope.parseURL = url;
        URLConnection connection = url.openConnection();
        if (JavaCIPUnknownScope.charset == null) {
            JavaCIPUnknownScope.charset = Charset.forName("UTF-8");
        }
        Reader reader = new InputStreamReader(connection.getInputStream(), JavaCIPUnknownScope.charset);
        return JavaCIPUnknownScope.internalParse(reader, target);
    }
}
