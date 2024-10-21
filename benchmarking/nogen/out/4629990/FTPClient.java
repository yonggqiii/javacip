// added by JavaCIP
public interface FTPClient {

    public abstract void disconnect();

    public abstract void connect(String arg0, boolean arg1);

    public abstract int getReplyCode();

    public abstract boolean login(boolean arg0, boolean arg1);

    public abstract String printWorkingDirectory();
}
