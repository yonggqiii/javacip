// added by JavaCIP
public interface File {

    public abstract boolean exists();

    public static File createTempFile(String arg0, String arg1) {
        return null;
    }

    public abstract void deleteOnExit();
}
