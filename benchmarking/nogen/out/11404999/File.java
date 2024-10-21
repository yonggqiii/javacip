// added by JavaCIP
public interface File {

    public static File createTempFile(String arg0, String arg1) {
        return null;
    }

    public abstract boolean delete();

    public abstract boolean exists();

    public abstract void renameTo(File arg0);
}
