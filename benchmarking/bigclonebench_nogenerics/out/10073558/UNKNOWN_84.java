// added by JavaCIP
public interface UNKNOWN_84 {

    public abstract void disconnect();

    public abstract FTPFile[] listFiles();

    public abstract void connect(boolean arg0, boolean arg1);

    public abstract void retrieveFile(byte arg0, FileOutputStream arg1);

    public abstract void setFileType(boolean arg0);

    public abstract void enterLocalPassiveMode();

    public abstract void changeWorkingDirectory(int arg0);

    public abstract void setRemoteVerificationEnabled(boolean arg0);

    public abstract boolean login(boolean arg0, boolean arg1);
}
