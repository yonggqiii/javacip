class c6597125 {

    public static void writeToFile(final File file, final InputStream in) throws IORuntimeException {
        IOUtils.createFile(file);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            IOUtils.copyStream(in, fos);
        } finally {
            IOUtils.closeIO(fos);
        }
    }
}
