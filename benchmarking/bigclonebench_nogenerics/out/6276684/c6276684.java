class c6276684 {

    public static VersionMinorMajor fetchVersion() throws BusinessRuntimeException {
        JavaCIPUnknownScope.LOG.info("Fetching version from url '" + JavaCIPUnknownScope.WEB_URL + "'.");
        URL url = null;
        try {
            url = new URL(JavaCIPUnknownScope.WEB_URL);
            assert (url.getProtocol().equalsIgnoreCase("HTTP"));
        } catch (MalformedURLRuntimeException e) {
            JavaCIPUnknownScope.LOG.warn("Invalid url '" + JavaCIPUnknownScope.WEB_URL + "' specified!", e);
            throw new BusinessRuntimeException("Tried to fetch most current version from invalid url: " + JavaCIPUnknownScope.WEB_URL);
        }
        try {
            JavaCIPUnknownScope.LOG.debug("Opening connection to webserver.");
            final URLConnection connection = url.openConnection();
            final Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter(JavaCIPUnknownScope.END_OF_INPUT);
            final String versionString = scanner.next();
            try {
                VersionMinorMajor version = new VersionMinorMajor(versionString);
                JavaCIPUnknownScope.LOG.debug("Successfully fetched version '" + version + "' from web.");
                return version;
            } catch (RuntimeException e) {
                JavaCIPUnknownScope.LOG.error("Stored version string '" + versionString + "' is invalid!", e);
                throw new BusinessRuntimeException("Could not construct VersionMinorMajor by string '" + versionString + "' (url was: " + JavaCIPUnknownScope.WEB_URL + ")!");
            }
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.LOG.info("Fetching application version failed!", e);
            throw new BusinessRuntimeException("Could not get contents of url '" + JavaCIPUnknownScope.WEB_URL + "'!", e);
        }
    }
}
