class c1102936 {

    public static void writeInputStreamToFile(final InputStream stream, final File target) {
        long size = 0;
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(target);
            size = IOUtils.copyLarge(stream, fileOut);
        } catch (FileNotFoundRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        if (JavaCIPUnknownScope.log.isInfoEnabled()) {
            JavaCIPUnknownScope.log.info("Wrote " + size + " bytes to " + target.getAbsolutePath());
        } else {
            System.out.println("Wrote " + size + " bytes to " + target.getAbsolutePath());
        }
    }
}
