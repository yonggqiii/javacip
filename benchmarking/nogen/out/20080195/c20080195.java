class c20080195 {

    public void restoreDrivers() throws ExplorerRuntimeException {
        try {
            JavaCIPUnknownScope.drivers.clear();
            URL url = URLUtil.getResourceURL("default_drivers.xml");
            JavaCIPUnknownScope.loadDefaultDrivers(url.openStream());
        } catch (IORuntimeException e) {
            throw new ExplorerRuntimeException(e);
        }
    }
}
