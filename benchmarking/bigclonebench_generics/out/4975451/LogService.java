// added by JavaCIP
public interface LogService {

    public static LogService getRoot() {
        return null;
    }

    public abstract void info(String arg0);

    public abstract void warning(String arg0);
}
