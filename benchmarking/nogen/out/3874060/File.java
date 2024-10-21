// added by JavaCIP
public interface File {

    public abstract File getParentFile();

    public abstract boolean exists();

    public abstract boolean mkdirs();

    public abstract void delete();
}
