class c20267500 {

    public static AudioInputStream getWavFromURL(String urlstr) {
        URL url;
        AudioInputStream ais = null;
        try {
            url = new URL(urlstr);
            URLConnection c = url.openConnection();
            c.connect();
            InputStream stream = c.getInputStream();
            ais = new AudioInputStream(stream, JavaCIPUnknownScope.playFormat, AudioSystem.NOT_SPECIFIED);
            JavaCIPUnknownScope.LOG.debug("[getWavFromURL]Getting audio from URL: {0}");
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return ais;
    }
}
