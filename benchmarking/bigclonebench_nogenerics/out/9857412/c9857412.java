class c9857412 {

    public void writeData(byte[] data, byte[] options, boolean transferMetaData) throws Throwable {
        long startTime = System.currentTimeMillis();
        long transferredBytesNum = 0;
        long elapsedTime = 0;
        Properties opts = PropertiesUtils.deserializeProperties(options);
        String server = opts.getProperty(JavaCIPUnknownScope.TRANSFER_OPTION_SERVER);
        String username = opts.getProperty(JavaCIPUnknownScope.TRANSFER_OPTION_USERNAME);
        String password = opts.getProperty(JavaCIPUnknownScope.TRANSFER_OPTION_PASSWORD);
        String filePath = opts.getProperty(JavaCIPUnknownScope.TRANSFER_OPTION_FILEPATH);
        if (transferMetaData) {
            int idx = filePath.lastIndexOf(JavaCIPUnknownScope.PATH_SEPARATOR);
            if (idx != -1) {
                String fileName = filePath.substring(idx + 1) + JavaCIPUnknownScope.META_DATA_FILE_SUFIX;
                filePath = filePath.substring(0, idx);
                filePath = filePath + JavaCIPUnknownScope.PATH_SEPARATOR + fileName;
            } else {
                filePath += JavaCIPUnknownScope.META_DATA_FILE_SUFIX;
            }
        }
        URL url = new URL(JavaCIPUnknownScope.PROTOCOL_PREFIX + username + ":" + password + "@" + server + filePath + ";type=i");
        URLConnection urlc = url.openConnection(BackEnd.getProxy(Proxy.Type.SOCKS));
        urlc.setConnectTimeout(Preferences.getInstance().preferredTimeOut * 1000);
        urlc.setReadTimeout(Preferences.getInstance().preferredTimeOut * 1000);
        OutputStream os = urlc.getOutputStream();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        byte[] buffer = new byte[1024];
        int br;
        while ((br = bis.read(buffer)) > 0) {
            os.write(buffer, 0, br);
            if (!transferMetaData) {
                transferredBytesNum += br;
                elapsedTime = System.currentTimeMillis() - startTime;
                JavaCIPUnknownScope.fireOnProgressEvent(transferredBytesNum, elapsedTime);
            }
        }
        bis.close();
        os.close();
    }
}
