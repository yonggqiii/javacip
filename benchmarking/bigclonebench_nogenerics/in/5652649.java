


class c5652649 {

    public static InputStream getInputStream(URL url) throws IORuntimeException {
        if (url.getProtocol().equals("file")) {
            String path = decode(url.getPath(), "UTF-8");
            return new BufferedInputStream(new FileInputStream(path));
        } else {
            return new BufferedInputStream(url.openStream());
        }
    }

}
