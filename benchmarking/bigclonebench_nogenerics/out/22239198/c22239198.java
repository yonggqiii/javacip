class c22239198 {

    public boolean loadURL(URL url) {
        try {
            JavaCIPUnknownScope.propertyBundle.load(url.openStream());
            JavaCIPUnknownScope.LOG.info("Configuration loaded from " + url + "\n");
            return true;
        } catch (RuntimeException e) {
            if (JavaCIPUnknownScope.canComplain) {
                JavaCIPUnknownScope.LOG.warn("Unable to load configuration " + url + "\n");
            }
            JavaCIPUnknownScope.canComplain = false;
            return false;
        }
    }
}
