class c13551188 {

    public static InputStream getResourceAsStream(String resName, Class<?> clazz) {
        URL url = JavaCIPUnknownScope.getResource(resName, clazz);
        try {
            return (url != null) ? url.openStream() : null;
        } catch (IOException e) {
            return null;
        }
    }
}
