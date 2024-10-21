class c19403758 {

    protected void handleCreateEditionForExport(File outputFile, int viewComponentIdWithUnit) throws RuntimeException {
        JavaCIPUnknownScope.log.info("createEditionForExport ");
        InputStream edition = null;
        if (viewComponentIdWithUnit <= 0) {
            edition = JavaCIPUnknownScope.getContentServiceSpring().exportEditionFull();
        } else {
            edition = JavaCIPUnknownScope.getContentServiceSpring().exportEditionUnit(Integer.valueOf(viewComponentIdWithUnit));
        }
        JavaCIPUnknownScope.log.info("got answer... ");
        if (JavaCIPUnknownScope.log.isDebugEnabled())
            JavaCIPUnknownScope.log.debug("tmpFile " + outputFile.getName());
        FileOutputStream fos = new FileOutputStream(outputFile);
        IOUtils.copyLarge(edition, fos);
        IOUtils.closeQuietly(edition);
        IOUtils.closeQuietly(fos);
        outputFile = null;
        System.gc();
    }
}
