// added by JavaCIP
public interface DesktopAppJax2 {

    public abstract boolean hasRightUpload();

    public abstract long getNewFileDataCenter(String arg0, String arg1);

    public abstract String uploadFinishFile(String arg0, String arg1, long arg2, boolean arg3);

    public abstract String createUploadSessionKey(String arg0, String arg1, int arg2);

    public abstract String getUploadFormUrl(int arg0, String arg1);

    public abstract long uploadStartFile(String arg0, String arg1, int arg2, boolean arg3, boolean arg4);

    public abstract String login(String arg0, String arg1);

    public abstract boolean getFileDownloadLink(String arg0, String arg1, long arg2);
}
