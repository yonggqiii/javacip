


class c8181418 {

    public static final byte[] getBytesFromUrl(final String urlString) throws BT747RuntimeException {
        byte[] result = null;
        try {
            final URL url = new URL(urlString);
            final URLConnection urlc = url.openConnection();
            urlc.setConnectTimeout(timeout);
            urlc.setReadTimeout(timeout);
            final InputStream ins = urlc.getInputStream();
            final ByteArrayOutputStream bout = new ByteArrayOutputStream(120 * 1024);
            final byte[] buf = new byte[1024];
            while (true) {
                final int n = ins.read(buf);
                if (n == -1) {
                    break;
                }
                bout.write(buf, 0, n);
            }
            result = bout.toByteArray();
            bout.close();
        } catch (final RuntimeException e) {
            throw new BT747RuntimeException(I18N.i18n("Problem downloading AGPS data."), e);
        }
        return result;
    }

}
