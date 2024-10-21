// added by JavaCIP
public interface UNKNOWN_135 {

    public abstract void setFileType(boolean arg0);

    public abstract void login(String arg0, String arg1);

    public abstract boolean retrieveFileStream(String arg0);

    public abstract void logout();

    public abstract void disconnect();

    public abstract boolean isConnected();

    public abstract void connect(boolean arg0);

    public abstract boolean getBufferSize();

    public abstract int getReplyCode();

    public abstract void completePendingCommand();
}
