class c17896475 {

    public String installCode(String serviceName, String location) throws DeploymentRuntimeException {
        FileOutputStream out = null;
        JavaCIPUnknownScope.mLog.debug("overwriteWarFile = " + JavaCIPUnknownScope.overwriteWarFile);
        String fileData = null;
        String filepath = location;
        String[] splitString = filepath.split("/");
        String filename = splitString[splitString.length - 1];
        int fileNameLength = filename.length();
        JavaCIPUnknownScope.warname = filename.substring(0, fileNameLength - 4);
        JavaCIPUnknownScope.mLog.debug("WAR file name = " + JavaCIPUnknownScope.warname);
        String filepath2 = JavaCIPUnknownScope.warDesination + File.separator + filename;
        JavaCIPUnknownScope.ret = "http://" + JavaCIPUnknownScope.containerAddress + "/" + JavaCIPUnknownScope.warname + "/services/" + serviceName;
        JavaCIPUnknownScope.mLog.debug("filepath2 = " + filepath2);
        JavaCIPUnknownScope.mLog.debug("ret = " + JavaCIPUnknownScope.ret);
        JavaCIPUnknownScope.mLog.debug("filepath = " + filepath);
        boolean warExists = new File(filepath2).exists();
        boolean webAppExists = true;
        try {
            String webAppName = filepath2.substring(0, (filepath2.length() - 4));
            JavaCIPUnknownScope.mLog.debug("Web Application Name = " + webAppName);
            webAppExists = new File(webAppName).isDirectory();
            if (!webAppExists) {
                URL url = new URL(filepath);
                File targetFile = new File(filepath2);
                if (!targetFile.exists()) {
                    targetFile.createNewFile();
                }
                InputStream in = null;
                try {
                    in = url.openStream();
                    out = new FileOutputStream(targetFile);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    throw new DeploymentRuntimeException("couldn't open stream due to: " + e.getMessage());
                }
                URLConnection con = url.openConnection();
                int fileLength = con.getContentLength();
                ReadableByteChannel channelIn = Channels.newChannel(in);
                FileChannel channelOut = out.getChannel();
                channelOut.transferFrom(channelIn, 0, fileLength);
                channelIn.close();
                channelOut.close();
                out.flush();
                out.close();
                in.close();
                long time = System.currentTimeMillis();
                JavaCIPUnknownScope.check(JavaCIPUnknownScope.ret, time, JavaCIPUnknownScope.STARTCONTROL);
            }
        } catch (RuntimeException e) {
            webAppExists = false;
        }
        JavaCIPUnknownScope.mLog.debug("webAppExists = " + webAppExists);
        return (JavaCIPUnknownScope.ret);
    }
}
