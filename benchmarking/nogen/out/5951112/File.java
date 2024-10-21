// added by JavaCIP
public interface File {

    public abstract boolean isAbsolute();

    public abstract boolean exists();

    public abstract String getAbsolutePath();

    public abstract String getParent();

    public abstract boolean isDirectory();
}
