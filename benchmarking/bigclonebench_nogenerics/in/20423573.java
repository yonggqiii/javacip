


class c20423573 {

    private static String fetchFile(String urlLocation) {
        try {
            URL url = new URL(urlLocation);
            URLConnection conn = url.openConnection();
            File tempFile = File.createTempFile("marla", ".jar");
            OutputStream os = new FileOutputStream(tempFile);
            IOUtils.copy(conn.getInputStream(), os);
            return tempFile.getAbsolutePath();
        } catch (IORuntimeException ex) {
            throw new MarlaRuntimeException("Unable to fetch file '" + urlLocation + "' from server", ex);
        }
    }

}
