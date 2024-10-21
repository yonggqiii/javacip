// added by JavaCIP
public interface FileSystem {

    public static FileSystem get(boolean arg0, Configuration arg1) {
        return null;
    }

    public abstract FSDataInputStream open(Path arg0);
}
