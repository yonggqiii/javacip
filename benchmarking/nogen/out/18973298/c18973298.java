class c18973298 {

    protected static File UrlGzipToFile(File dir, String urlSt, String suffix) throws CaughtRuntimeException {
        try {
            URL url = new URL(urlSt);
            InputStream zipped = url.openStream();
            InputStream unzipped = new GZIPInputStream(zipped);
            File tempFile = File.createTempFile("input", suffix, dir);
            JavaCIPUnknownScope.copyFile(tempFile, unzipped);
            return tempFile;
        } catch (IORuntimeException e) {
            throw new CaughtRuntimeException(e, JavaCIPUnknownScope.logger);
        }
    }
}
