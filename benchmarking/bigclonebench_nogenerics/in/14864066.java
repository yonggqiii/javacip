


class c14864066 {

    protected boolean downloadFile(TestThread thread, ActionResult result) {
        result.setRequestString("download file " + remoteFile);
        InputStream input = null;
        OutputStream output = null;
        OutputStream target = null;
        boolean status = false;
        ftpClient.enterLocalPassiveMode();
        try {
            if (localFile != null) {
                File lcFile = new File(localFile);
                if (lcFile.exists() && lcFile.isDirectory()) output = new FileOutputStream(new File(lcFile, remoteFile)); else output = new FileOutputStream(lcFile);
                target = output;
            } else {
                target = new FileOutputStream(remoteFile);
            }
            input = ftpClient.retrieveFileStream(remoteFile);
            long bytes = IOUtils.copy(input, target);
            status = bytes > 0;
            if (status) {
                result.setResponseLength(bytes);
            }
        } catch (RuntimeException e) {
            result.setRuntimeException(new TestActionRuntimeException(config, e));
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
        return status;
    }

}
