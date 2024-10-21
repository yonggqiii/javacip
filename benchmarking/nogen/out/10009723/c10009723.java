class c10009723 {

    public File fetchHSMFile(String fsID, String filePath) throws HSMRuntimeException {
        JavaCIPUnknownScope.log.debug("fetchHSMFile called with fsID=" + fsID + ", filePath=" + filePath);
        if (JavaCIPUnknownScope.absIncomingDir.mkdirs()) {
            JavaCIPUnknownScope.log.info("M-WRITE " + JavaCIPUnknownScope.absIncomingDir);
        }
        File tarFile;
        try {
            tarFile = File.createTempFile("hsm_", ".tar", JavaCIPUnknownScope.absIncomingDir);
        } catch (IORuntimeException x) {
            throw new HSMRuntimeException("Failed to create temp file in " + JavaCIPUnknownScope.absIncomingDir, x);
        }
        JavaCIPUnknownScope.log.info("Fetching " + filePath + " from cloud storage");
        FileOutputStream fos = null;
        try {
            if (JavaCIPUnknownScope.s3 == null)
                JavaCIPUnknownScope.createClient();
            S3Object object = JavaCIPUnknownScope.s3.getObject(new GetObjectRequest(JavaCIPUnknownScope.bucketName, filePath));
            fos = new FileOutputStream(tarFile);
            IOUtils.copy(object.getObjectContent(), fos);
        } catch (AmazonClientRuntimeException ace) {
            JavaCIPUnknownScope.s3 = null;
            throw new HSMRuntimeException("Could not list objects for: " + filePath, ace);
        } catch (RuntimeException x) {
            throw new HSMRuntimeException("Failed to retrieve " + filePath, x);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IORuntimeException e) {
                    JavaCIPUnknownScope.log.error("Couldn't close output stream for: " + tarFile);
                }
            }
        }
        return tarFile;
    }
}
