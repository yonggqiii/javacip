class c6240406 {

    public static Image loadImage(URL url) throws IORuntimeException {
        BufferedInputStream in = new BufferedInputStream(url.openStream());
        try {
            return JavaCIPUnknownScope.getLoader(url.getFile()).loadImage(in);
        } finally {
            in.close();
        }
    }
}
