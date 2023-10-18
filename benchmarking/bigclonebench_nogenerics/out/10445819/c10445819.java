class c10445819 {

    private Reader getReader() throws IORuntimeException {
        if (JavaCIPUnknownScope.data != null) {
            if (JavaCIPUnknownScope.url != null)
                throw new IllegalArgumentRuntimeException("URL for source data and the data itself must never be specified together.");
            if (JavaCIPUnknownScope.charset != null)
                throw new IllegalArgumentRuntimeException("Charset has sense only for URL-based data");
            return new StringReader(JavaCIPUnknownScope.data);
        } else if (JavaCIPUnknownScope.url != null) {
            InputStream stream = JavaCIPUnknownScope.url.openStream();
            if (JavaCIPUnknownScope.charset == null)
                return new InputStreamReader(stream);
            else
                return new InputStreamReader(stream, JavaCIPUnknownScope.charset);
        }
        return null;
    }
}
