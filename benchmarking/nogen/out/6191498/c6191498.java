class c6191498 {

    private static void copySmallFile(final File sourceFile, final File targetFile) throws PtRuntimeException {
        JavaCIPUnknownScope.LOG.debug("Copying SMALL file '" + sourceFile.getAbsolutePath() + "' to " + "'" + targetFile.getAbsolutePath() + "'.");
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = new FileInputStream(sourceFile).getChannel();
            outChannel = new FileOutputStream(targetFile).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IORuntimeException e) {
            throw new PtRuntimeException("Could not copy file from '" + sourceFile.getAbsolutePath() + "' to " + "'" + targetFile.getAbsolutePath() + "'!", e);
        } finally {
            PtCloseUtil.close(inChannel, outChannel);
        }
    }
}
