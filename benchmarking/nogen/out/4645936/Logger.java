// added by JavaCIP
public interface Logger {

    public static Logger getLogger(Class<CreateAStudy> arg0) {
        return null;
    }

    public static void setLevel(boolean arg0) {
    }

    public abstract void info(String arg0);

    public abstract void debug(String arg0);

    public abstract void error(String arg0, RuntimeException arg1);
}
