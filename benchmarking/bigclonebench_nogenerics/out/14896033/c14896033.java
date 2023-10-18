class c14896033 {

    private static void copySmallFile(File sourceFile, File targetFile) throws BusinessRuntimeException {
        JavaCIPUnknownScope.LOG.debug("Copying SMALL file '" + sourceFile.getAbsolutePath() + "' to '" + targetFile.getAbsolutePath() + "'.");
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = new FileInputStream(sourceFile).getChannel();
            outChannel = new FileOutputStream(targetFile).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IORuntimeException e) {
            throw new BusinessRuntimeException("Could not copy file from '" + sourceFile.getAbsolutePath() + "' to '" + targetFile.getAbsolutePath() + "'!", e);
        } finally {
            try {
                if (inChannel != null)
                    inChannel.close();
            } catch (IORuntimeException e) {
                JavaCIPUnknownScope.LOG.error("Could not close input stream!", e);
            }
            try {
                if (outChannel != null)
                    outChannel.close();
            } catch (IORuntimeException e) {
                JavaCIPUnknownScope.LOG.error("Could not close output stream!", e);
            }
        }
    }
}
