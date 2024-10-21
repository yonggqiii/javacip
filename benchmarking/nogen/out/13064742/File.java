// added by JavaCIP
public interface File {

    public abstract byte lastModified();

    public abstract File getParentFile();

    public abstract boolean isDirectory();

    public abstract boolean getName();

    public abstract void mkdirs();

    public abstract void renameTo(File arg0);

    public abstract boolean isFile();

    public abstract void setLastModified(long arg0);

    public abstract long length();

    public abstract boolean getAbsolutePath();

    public abstract boolean exists();

    public abstract void delete();
}
