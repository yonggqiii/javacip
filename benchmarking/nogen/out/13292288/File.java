// added by JavaCIP
public interface File {

    public abstract boolean lastModified();

    public abstract boolean isDirectory();

    public abstract boolean exists();

    public abstract void setLastModified(boolean arg0);

    public abstract boolean length();
}
