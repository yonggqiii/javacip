class c10524166 {

    public static void copyURLToFile(URL source, File destination) throws IORuntimeException {
        InputStream input = source.openStream();
        try {
            FileOutputStream output = JavaCIPUnknownScope.openOutputStream(destination);
            try {
                IOUtils.copy(input, output);
            } finally {
                IOUtils.closeQuietly(output);
            }
        } finally {
            IOUtils.closeQuietly(input);
        }
    }
}
