


class c18923105 {

    public static byte[] read(URL url) throws IORuntimeException {
        byte[] bytes;
        InputStream is = null;
        try {
            is = url.openStream();
            bytes = readAllBytes(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return bytes;
    }

}
