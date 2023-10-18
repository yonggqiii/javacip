// added by JavaCIP
public interface File {

    public abstract boolean getPath();

    public abstract boolean renameTo(File arg0);

    public abstract boolean isFile();

    public static File createTempFile(boolean arg0, String arg1, boolean arg2) {
        return null;
    }

    public abstract boolean exists();

    public abstract void delete();
}
