// added by JavaCIP
public interface File {

    public abstract File getParentFile();

    public abstract void mkdirs();

    public abstract boolean exists();

    public abstract boolean getAbsolutePath();
}
