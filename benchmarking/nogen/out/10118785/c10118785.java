class c10118785 {

    public void download(String remoteFilePath, String localFilePath) {
        InputStream remoteStream = null;
        try {
            remoteStream = JavaCIPUnknownScope.client.get(remoteFilePath);
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        OutputStream localStream = null;
        try {
            localStream = new FileOutputStream(new File(localFilePath));
        } catch (FileNotFoundRuntimeException e1) {
            e1.printStackTrace();
        }
        try {
            IOUtils.copy(remoteStream, localStream);
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }
}
