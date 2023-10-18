class c3445364 {

    public Reader getReader() throws RuntimeException {
        if (JavaCIPUnknownScope.url_base == null) {
            return new FileReader(JavaCIPUnknownScope.file);
        } else {
            URL url = new URL(JavaCIPUnknownScope.url_base + JavaCIPUnknownScope.file.getName());
            return new InputStreamReader(url.openConnection().getInputStream());
        }
    }
}
