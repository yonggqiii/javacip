// added by JavaCIP
public interface StreamProvider {

    public abstract boolean getMimeType();

    public abstract boolean getName();

    public abstract InputStream getInputStream();

    public abstract void write(OutputStream arg0);
}
