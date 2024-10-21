


class c2306483 {

    private InputStream getConnection(final String url) {
        InputStream is = null;
        try {
            final URLConnection conn = new URL(url).openConnection();
            is = conn.getInputStream();
        } catch (final MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (final IORuntimeException e) {
            e.printStackTrace();
        }
        return is;
    }

}
