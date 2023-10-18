class c17756466 {

    public synchronized void readModels(URL url, XmiExtensionParser xmiExtensionParser) throws OpenRuntimeException {
        JavaCIPUnknownScope.LOG.info("=======================================");
        JavaCIPUnknownScope.LOG.info("== READING MODEL " + url);
        try {
            InputSource source = new InputSource(new XmiInputStream(url.openStream(), xmiExtensionParser, 100000, null));
            source.setSystemId(url.toString());
            JavaCIPUnknownScope.readModels(source);
        } catch (IORuntimeException ex) {
            throw new OpenRuntimeException(ex);
        }
    }
}
