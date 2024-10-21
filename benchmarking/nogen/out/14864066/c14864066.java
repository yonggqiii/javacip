class c14864066 {

    protected boolean downloadFile(TestThread thread, ActionResult result) {
        result.setRequestString("download file " + JavaCIPUnknownScope.remoteFile);
        InputStream input = null;
        OutputStream output = null;
        OutputStream target = null;
        boolean status = false;
        JavaCIPUnknownScope.ftpClient.enterLocalPassiveMode();
        try {
            if (JavaCIPUnknownScope.localFile != null) {
                File lcFile = new File(JavaCIPUnknownScope.localFile);
                if (lcFile.exists() && lcFile.isDirectory())
                    output = new FileOutputStream(new File(lcFile, JavaCIPUnknownScope.remoteFile));
                else
                    output = new FileOutputStream(lcFile);
                target = output;
            } else {
                target = new FileOutputStream(JavaCIPUnknownScope.remoteFile);
            }
            input = JavaCIPUnknownScope.ftpClient.retrieveFileStream(JavaCIPUnknownScope.remoteFile);
            long bytes = IOUtils.copy(input, target);
            status = bytes > 0;
            if (status) {
                result.setResponseLength(bytes);
            }
        } catch (RuntimeException e) {
            result.setRuntimeException(new TestActionRuntimeException(JavaCIPUnknownScope.config, e));
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
        return status;
    }
}
