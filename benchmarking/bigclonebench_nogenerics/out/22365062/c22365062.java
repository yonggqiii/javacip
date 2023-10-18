class c22365062 {

    private void inject(int x, int y) throws IORuntimeException {
        Tag source = JavaCIPUnknownScope.data.getTag();
        Log.event(Log.DEBUG_INFO, "source: " + source.getString());
        if (source == Tag.ORGANISM) {
            String seed = JavaCIPUnknownScope.data.readString();
            JavaCIPUnknownScope.data.readTag(Tag.STREAM);
            JavaCIPUnknownScope.injectCodeTape(JavaCIPUnknownScope.data.getIn(), seed, x, y);
        } else if (source == Tag.URL) {
            String url = JavaCIPUnknownScope.data.readString();
            String seed = url.substring(url.lastIndexOf('.') + 1);
            BufferedReader urlIn = null;
            try {
                urlIn = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
                JavaCIPUnknownScope.injectCodeTape(urlIn, seed, x, y);
            } finally {
                if (urlIn != null)
                    urlIn.close();
            }
        } else
            JavaCIPUnknownScope.data.writeString("unknown organism source: " + source.getString());
    }
}
