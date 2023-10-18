class c2955074 {

    public static void copyFile(File source, File destination) throws IORuntimeException {
        if (source == null) {
            String message = Logging.getMessage("nullValue.SourceIsNull");
            Logging.logger().severe(message);
            throw new IllegalArgumentRuntimeException(message);
        }
        if (destination == null) {
            String message = Logging.getMessage("nullValue.DestinationIsNull");
            Logging.logger().severe(message);
            throw new IllegalArgumentRuntimeException(message);
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fic, foc;
        try {
            fis = new FileInputStream(source);
            fic = fis.getChannel();
            fos = new FileOutputStream(destination);
            foc = fos.getChannel();
            foc.transferFrom(fic, 0, fic.size());
            fos.flush();
            fis.close();
            fos.close();
        } finally {
            JavaCIPUnknownScope.WWIO.closeStream(fis, source.getPath());
            JavaCIPUnknownScope.WWIO.closeStream(fos, destination.getPath());
        }
    }
}
