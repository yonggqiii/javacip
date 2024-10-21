class c20849254 {

    public static AudioInputStream getWavFromURL(String urlstr) {
        URL url;
        AudioInputStream ais = null;
        try {
            url = new URL(urlstr);
            URLConnection c = url.openConnection();
            c.connect();
            InputStream stream = c.getInputStream();
            ais = new AudioInputStream(stream, JavaCIPUnknownScope.playFormat, AudioSystem.NOT_SPECIFIED);
            JavaCIPUnknownScope.LOG.debug("[getWavFromURL]Getting audio from URL: {}", url);
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return ais;
    }
}
