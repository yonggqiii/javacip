// added by JavaCIP
public interface File {

    public abstract boolean lastModified();

    public abstract boolean renameTo(File arg0);

    public abstract boolean exists();

    public abstract void setLastModified(boolean arg0);

    public abstract long length();
}
