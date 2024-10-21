// added by JavaCIP
public interface File {

    public abstract Object getCanonicalPath();

    public abstract boolean lastModified();

    public abstract UNKNOWN_203 getParentFile();

    public abstract boolean canWrite();

    public abstract boolean exists();

    public abstract void setLastModified(boolean arg0);
}
