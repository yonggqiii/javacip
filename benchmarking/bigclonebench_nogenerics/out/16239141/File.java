// added by JavaCIP
public interface File {

    public abstract boolean exists();

    public abstract void delete();

    public abstract boolean renameTo(File arg0);

    public abstract boolean getAbsolutePath();
}