// added by JavaCIP
public interface File {

    public abstract boolean getName();

    public abstract boolean isDirectory();

    public abstract File[] listFiles();

    public abstract boolean isFile();
}
