// added by JavaCIP
public interface File {

    public abstract boolean isDirectory();

    public abstract boolean isFile();

    public abstract void setLastModified(long arg0);

    public abstract long length();

    public abstract String getAbsolutePath();

    public abstract boolean exists();

    public abstract void delete();
}
