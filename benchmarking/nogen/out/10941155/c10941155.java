class c10941155 {

    private void detachFile(File file, Block b) throws IORuntimeException {
        File tmpFile = JavaCIPUnknownScope.volume.createDetachFile(b, file.getName());
        try {
            IOUtils.copyBytes(new FileInputStream(file), new FileOutputStream(tmpFile), 16 * 1024, true);
            if (file.length() != tmpFile.length()) {
                throw new IORuntimeException("Copy of file " + file + " size " + file.length() + " into file " + tmpFile + " resulted in a size of " + tmpFile.length());
            }
            FileUtil.replaceFile(tmpFile, file);
        } catch (IORuntimeException e) {
            boolean done = tmpFile.delete();
            if (!done) {
                DataNode.LOG.info("detachFile failed to delete temporary file " + tmpFile);
            }
            throw e;
        }
    }
}
