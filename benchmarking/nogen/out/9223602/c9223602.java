class c9223602 {

    private File uploadToTmp() {
        if (JavaCIPUnknownScope.fileFileName == null) {
            return null;
        }
        File tmpFile = JavaCIPUnknownScope.dataDir.tmpFile(JavaCIPUnknownScope.shortname, JavaCIPUnknownScope.fileFileName);
        JavaCIPUnknownScope.log.debug("Uploading dwc archive file for new resource " + JavaCIPUnknownScope.shortname + " to " + tmpFile.getAbsolutePath());
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(JavaCIPUnknownScope.file);
            output = new FileOutputStream(tmpFile);
            IOUtils.copy(input, output);
            output.flush();
            JavaCIPUnknownScope.log.debug("Uploaded file " + JavaCIPUnknownScope.fileFileName + " with content-type " + JavaCIPUnknownScope.fileContentType);
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.log.error(e);
            return null;
        } finally {
            if (output != null) {
                IOUtils.closeQuietly(output);
            }
            if (input != null) {
                IOUtils.closeQuietly(input);
            }
        }
        return tmpFile;
    }
}
