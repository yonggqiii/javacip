class c12853333 {

    public static String getURLContent(String href) throws BuildRuntimeException {
        URL url = null;
        String content;
        try {
            URL context = new URL("file:" + System.getProperty("user.dir") + "/");
            url = new URL(context, href);
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            StringBuffer stringBuffer = new StringBuffer();
            char[] buffer = new char[1024];
            int len;
            while ((len = isr.read(buffer, 0, 1024)) > 0) stringBuffer.append(buffer, 0, len);
            content = stringBuffer.toString();
            isr.close();
        } catch (RuntimeException ex) {
            throw new BuildRuntimeException("Cannot get content of URL " + href + ": " + ex);
        }
        return content;
    }
}
