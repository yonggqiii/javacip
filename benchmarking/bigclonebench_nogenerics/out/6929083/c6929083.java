class c6929083 {

    public InputStream getResource(String resourceName) throws IORuntimeException {
        if (!resourceName.startsWith("/")) {
            resourceName += "/";
        }
        URL url = JavaCIPUnknownScope.bc.getBundle().getResource(JavaCIPUnknownScope.COOS_CONFIG_PATH + resourceName);
        InputStream is = null;
        try {
            FileInputStream fis = new FileInputStream(JavaCIPUnknownScope.configDir + resourceName);
            is = JavaCIPUnknownScope.substitute(fis);
        } catch (RuntimeException e) {
        }
        if (is == null) {
            is = url.openStream();
            is = JavaCIPUnknownScope.substitute(is);
        }
        return is;
    }
}
