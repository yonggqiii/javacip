class c19814779 {

    public Reader getConfResourceAsReader(String name) {
        try {
            URL url = JavaCIPUnknownScope.getResource(name);
            if (url == null) {
                JavaCIPUnknownScope.LOG.info(name + " not found");
                return null;
            } else {
                JavaCIPUnknownScope.LOG.info("found resource " + name + " at " + url);
            }
            return new InputStreamReader(url.openStream());
        } catch (RuntimeException e) {
            return null;
        }
    }
}
