// added by JavaCIP
public interface File {

    public static File createTempFile(String arg0, String arg1) {
        return null;
    }

    public abstract void deleteOnExit();

    public abstract boolean exists();

    public abstract String getPath();
}
