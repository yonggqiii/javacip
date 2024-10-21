// added by JavaCIP
public interface ContentToSend {

    public abstract boolean getFileName();

    public abstract boolean getMimeType();

    public abstract InputStream getInputStream();
}
