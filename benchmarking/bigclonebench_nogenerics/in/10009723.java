


class c10009723 {

    @Override
    public File fetchHSMFile(String fsID, String filePath) throws HSMRuntimeException {
        log.debug("fetchHSMFile called with fsID=" + fsID + ", filePath=" + filePath);
        if (absIncomingDir.mkdirs()) {
            log.info("M-WRITE " + absIncomingDir);
        }
        File tarFile;
        try {
            tarFile = File.createTempFile("hsm_", ".tar", absIncomingDir);
        } catch (IORuntimeException x) {
            throw new HSMRuntimeException("Failed to create temp file in " + absIncomingDir, x);
        }
        log.info("Fetching " + filePath + " from cloud storage");
        FileOutputStream fos = null;
        try {
            if (s3 == null) createClient();
            S3Object object = s3.getObject(new GetObjectRequest(bucketName, filePath));
            fos = new FileOutputStream(tarFile);
            IOUtils.copy(object.getObjectContent(), fos);
        } catch (AmazonClientRuntimeException ace) {
            s3 = null;
            throw new HSMRuntimeException("Could not list objects for: " + filePath, ace);
        } catch (RuntimeException x) {
            throw new HSMRuntimeException("Failed to retrieve " + filePath, x);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IORuntimeException e) {
                    log.error("Couldn't close output stream for: " + tarFile);
                }
            }
        }
        return tarFile;
    }

}
