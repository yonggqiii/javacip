class c4048013 {

    public InputStream getParameterAsInputStream(String key) throws UndefinedParameterError, IORuntimeException {
        String urlString = JavaCIPUnknownScope.getParameter(key);
        if (urlString == null)
            return null;
        try {
            URL url = new URL(urlString);
            InputStream stream = url.openStream();
            return stream;
        } catch (MalformedURLRuntimeException e) {
            File file = JavaCIPUnknownScope.getParameterAsFile(key);
            if (file != null) {
                return new FileInputStream(file);
            } else {
                return null;
            }
        }
    }
}
