// added by JavaCIP
public interface File {

    public abstract Object getParentFile();

    public abstract boolean createNewFile();

    public static File createTempFile(String arg0, String arg1) {
        return null;
    }

    public abstract boolean getAbsolutePath();

    public abstract boolean exists();

    public abstract void delete();
}
