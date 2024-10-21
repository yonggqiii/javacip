class c10689659 {

    public static void copyFile(File fromFile, File toFile) throws OWFileCopyRuntimeException {
        try {
            FileChannel src = new FileInputStream(fromFile).getChannel();
            FileChannel dest = new FileOutputStream(toFile).getChannel();
            dest.transferFrom(src, 0, src.size());
            src.close();
            dest.close();
        } catch (IORuntimeException e) {
            throw (new OWFileCopyRuntimeException("An error occurred while copying a file", e));
        }
    }
}
