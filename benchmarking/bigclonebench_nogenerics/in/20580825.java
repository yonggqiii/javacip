


class c20580825 {

    public static ByteBuffer readURL(URL url) throws IORuntimeException, MalformedURLRuntimeException {
        URLConnection connection = null;
        try {
            connection = url.openConnection();
            return readInputStream(new BufferedInputStream(connection.getInputStream()));
        } catch (IORuntimeException e) {
            throw e;
        }
    }

}
