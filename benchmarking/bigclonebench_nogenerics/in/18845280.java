


class c18845280 {

    public static void readFile(FOUserAgent ua, String uri, OutputStream output) throws IORuntimeException {
        InputStream in = getURLInputStream(ua, uri);
        try {
            IOUtils.copy(in, output);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

}
