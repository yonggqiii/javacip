class c8535388 {

    public static File downloadFile(URL url, String filePath) throws IORuntimeException, ConnectRuntimeException, UnknownHostRuntimeException {
        File f = null;
        long t1 = System.currentTimeMillis();
        try {
            long t3 = System.currentTimeMillis();
            File tempDirectory = new File(JavaCIPUnknownScope.tempDirectoryPath);
            if (!tempDirectory.exists()) {
                tempDirectory.mkdir();
            }
            String fName = JavaCIPUnknownScope.normalizeFileName(filePath);
            f = new File(JavaCIPUnknownScope.tempDirectoryPath + "/" + fName);
            JavaCIPUnknownScope.logger.info("downloading '" + url.toString() + "' to: " + f.getAbsolutePath());
            f.deleteOnExit();
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
            byte[] buffer = new byte[1024 * 256];
            InputStream is = url.openStream();
            long readed = 0;
            for (int i = is.read(buffer); i > 0; i = is.read(buffer)) {
                dos.write(buffer, 0, i);
                readed += i;
            }
            dos.close();
            long t4 = System.currentTimeMillis();
            JavaCIPUnknownScope.logger.debug("Download time: " + (t4 - t3));
        } catch (IORuntimeException io) {
            io.printStackTrace();
        }
        long t2 = System.currentTimeMillis();
        JavaCIPUnknownScope.logger.debug("Total download method time: " + (t2 - t1));
        return f;
    }
}
