class c17337015 {

    public boolean downloadFile(String webdir, String fileName, String localdir) {
        boolean result = false;
        JavaCIPUnknownScope.checkDownLoadDirectory(localdir);
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(JavaCIPUnknownScope.url);
            ftp.login(JavaCIPUnknownScope.username, JavaCIPUnknownScope.password);
            if (!"".equals(webdir.trim()))
                ftp.changeDirectory(webdir);
            ftp.download(fileName, new File(localdir + "//" + fileName));
            result = true;
            ftp.disconnect(true);
        } catch (IllegalStateRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        } catch (FTPIllegalReplyRuntimeException e) {
            e.printStackTrace();
        } catch (FTPRuntimeException e) {
            e.printStackTrace();
        } catch (FTPDataTransferRuntimeException e) {
            e.printStackTrace();
        } catch (FTPAbortedRuntimeException e) {
            e.printStackTrace();
        }
        return result;
    }
}
