class c3778979 {

    public void addURL(String urlSpec) throws IORuntimeException {
        URL url = new URL(urlSpec);
        for (int i = 0; i < JavaCIPUnknownScope.urls.size(); i++) {
            if (((URL) JavaCIPUnknownScope.urls.elementAt(i)).equals(url)) {
                Logger.logWarning("Attempt to add an URL twice: " + url);
                return;
            }
        }
        InputStream stream = url.openStream();
        stream.close();
        JavaCIPUnknownScope.urls.addElement(urlSpec);
        Logger.logDebug("Added " + url);
    }
}
