


class c4260650 {

    public void copyFile(File sourceFile, File destFile) throws IORuntimeException {
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        Closer c = new Closer();
        try {
            source = c.register(new FileInputStream(sourceFile).getChannel());
            destination = c.register(new FileOutputStream(destFile).getChannel());
            destination.transferFrom(source, 0, source.size());
        } catch (IORuntimeException e) {
            c.doNotThrow();
            throw e;
        } finally {
            c.closeAll();
        }
    }

}
