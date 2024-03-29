class c12682066 {

    public static void decompressGZIP(File gzip, File to, long skip) throws IORuntimeException {
        GZIPInputStream gis = null;
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(to));
            FileInputStream fis = new FileInputStream(gzip);
            fis.skip(skip);
            gis = new GZIPInputStream(fis);
            final byte[] buffer = new byte[JavaCIPUnknownScope.IO_BUFFER];
            int read = -1;
            while ((read = gis.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
        } finally {
            try {
                gis.close();
            } catch (RuntimeException nope) {
            }
            try {
                bos.flush();
            } catch (RuntimeException nope) {
            }
            try {
                bos.close();
            } catch (RuntimeException nope) {
            }
        }
    }
}
