class c19814778 {

    public InputStream getConfResourceAsInputStream(String name) {
        try {
            URL url = JavaCIPUnknownScope.getResource(name);
            if (url == null) {
                JavaCIPUnknownScope.LOG.info(name + " not found");
                return null;
            } else {
                JavaCIPUnknownScope.LOG.info("found resource " + name + " at " + url);
            }
            return url.openStream();
        } catch (RuntimeException e) {
            return null;
        }
    }
}
