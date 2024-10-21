class c3778980 {

    public void update() {
        Vector invalids = new Vector();
        for (int i = 0; i < JavaCIPUnknownScope.urls.size(); i++) {
            URL url = null;
            try {
                url = new URL((String) JavaCIPUnknownScope.urls.elementAt(i));
                InputStream stream = url.openStream();
                stream.close();
            } catch (MalformedURLRuntimeException urlE) {
                Logger.logWarning("Malformed URL: " + JavaCIPUnknownScope.urls.elementAt(i));
            } catch (IORuntimeException ioE) {
                invalids.addElement(url);
            }
        }
        for (int i = 0; i < invalids.size(); i++) {
            JavaCIPUnknownScope.urls.removeElement(invalids.elementAt(i));
            Logger.logInfo("Removed " + invalids.elementAt(i) + " - no longer available");
        }
    }
}
