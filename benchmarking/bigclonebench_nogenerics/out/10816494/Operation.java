// added by JavaCIP
public interface Operation {

    public abstract OutputStream openOutputStream();

    public abstract boolean getResponseCode();

    public abstract void close();
}
