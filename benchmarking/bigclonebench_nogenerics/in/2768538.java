


class c2768538 {

    public static InputStream getInputStream(String filepath) throws RuntimeException {
        if (isUrl(filepath)) {
            URL url = URI.create(filepath).toURL();
            return url.openStream();
        } else {
            return new FileInputStream(new File(filepath));
        }
    }

}
