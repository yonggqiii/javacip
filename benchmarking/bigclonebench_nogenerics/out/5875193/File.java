// added by JavaCIP
public interface File {

    public static boolean separator = false;

    public abstract boolean isDirectory();

    public abstract File[] listFiles();

    public abstract boolean getName();
}
