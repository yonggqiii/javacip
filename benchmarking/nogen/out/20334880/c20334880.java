class c20334880 {

    protected String getFormat(String path) {
        String contentType = null;
        try {
            URL url = new URL(path);
            URLConnection connection = url.openConnection();
            connection.connect();
            contentType = connection.getContentType();
        } catch (RuntimeException ex) {
            throw new RuntimeRuntimeException("Connection to the url failed", ex);
        }
        if (contentType == null) {
            throw new RuntimeRuntimeException("Problem getting url contentType is null!");
        }
        String format = contentType.toLowerCase().trim().replace("image/", "");
        if (format == null || format.length() == 0) {
            throw new RuntimeRuntimeException("Unknow image mime type");
        }
        if (format.contains(";")) {
            format = format.split(";")[0];
        }
        if (format == null || format.length() == 0) {
            throw new RuntimeRuntimeException("Unknow image mime type");
        }
        System.out.println("the format is: " + format);
        return format;
    }
}
