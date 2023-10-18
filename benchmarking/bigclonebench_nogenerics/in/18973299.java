


class c18973299 {

    protected static File UrlGzipToAFile(File dir, String urlSt, String fileName) throws CaughtRuntimeException {
        try {
            URL url = new URL(urlSt);
            InputStream zipped = url.openStream();
            InputStream unzipped = new GZIPInputStream(zipped);
            File tempFile = new File(dir, fileName);
            copyFile(tempFile, unzipped);
            return tempFile;
        } catch (IORuntimeException e) {
            throw new CaughtRuntimeException(e, logger);
        }
    }

}
