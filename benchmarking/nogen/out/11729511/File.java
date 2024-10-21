// added by JavaCIP
public interface File {

    public static File createTempFile(String arg0, String arg1) {
        return null;
    }

    public abstract void deleteOnExit();

    public abstract String getName();

    public abstract boolean getAbsolutePath();
}
