// added by JavaCIP
public interface Output {

    public abstract void addHeader(boolean arg0, boolean arg1);

    public abstract void setStatus(boolean arg0, boolean arg1);

    public abstract void open();

    public abstract void close();

    public abstract OutputStream getOutputStream();
}
