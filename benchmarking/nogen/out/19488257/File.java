// added by JavaCIP
public interface File {

    public abstract long lastModified();

    public abstract boolean isDirectory();

    public abstract void mkdirs();

    public abstract void setLastModified(long arg0);

    public abstract boolean length();

    public abstract boolean list();
}
