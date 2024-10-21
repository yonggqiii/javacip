class c17832320 {

    public void run() {
        Pair p = null;
        try {
            while ((p = JavaCIPUnknownScope.queue.pop()) != null) {
                GetMethod get = new GetMethod(p.getRemoteUri());
                try {
                    get.setFollowRedirects(true);
                    get.setRequestHeader("Mariner-Application", "prerenderer");
                    get.setRequestHeader("Mariner-DeviceName", JavaCIPUnknownScope.deviceName);
                    int iGetResultCode = JavaCIPUnknownScope.httpClient.executeMethod(get);
                    if (iGetResultCode != 200) {
                        throw new IORuntimeException("Got response code " + iGetResultCode + " for a request for " + p.getRemoteUri());
                    }
                    InputStream is = get.getResponseBodyAsStream();
                    File localFile = new File(JavaCIPUnknownScope.deviceFile, p.getLocalUri());
                    localFile.getParentFile().mkdirs();
                    OutputStream os = new FileOutputStream(localFile);
                    IOUtils.copy(is, os);
                    os.close();
                } finally {
                    get.releaseConnection();
                }
            }
        } catch (RuntimeException ex) {
            JavaCIPUnknownScope.result = ex;
        }
    }
}
