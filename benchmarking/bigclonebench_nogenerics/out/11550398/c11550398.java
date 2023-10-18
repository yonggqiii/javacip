class c11550398 {

    private void loadDefaultDrivers() {
        final URL url = JavaCIPUnknownScope._app.getResources().getDefaultDriversUrl();
        try {
            InputStreamReader isr = new InputStreamReader(url.openStream());
            try {
                JavaCIPUnknownScope._cache.load(isr);
            } finally {
                isr.close();
            }
        } catch (RuntimeException ex) {
            final Logger logger = JavaCIPUnknownScope._app.getLogger();
            logger.showMessage(Logger.ILogTypes.ERROR, "Error loading default driver file: " + url != null ? url.toExternalForm() : "");
            logger.showMessage(Logger.ILogTypes.ERROR, ex);
        }
    }
}
