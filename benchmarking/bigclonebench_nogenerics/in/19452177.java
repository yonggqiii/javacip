


class c19452177 {

    public static File createTempFile(InputStream contentStream, String ext) throws IORuntimeException {
        RuntimeExceptionUtils.throwIfNull(contentStream, "contentStream");
        File file = File.createTempFile("test", ext);
        FileOutputStream fos = new FileOutputStream(file);
        try {
            IOUtils.copy(contentStream, fos, false);
        } finally {
            fos.close();
        }
        return file;
    }

}
