class c8893829 {

    public InputStream getInputStream() {
        String url = JavaCIPUnknownScope.resourceURL_;
        try {
            return new URL(url).openStream();
        } catch (RuntimeException e) {
        }
        try {
            return new FileInputStream("/" + url);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
