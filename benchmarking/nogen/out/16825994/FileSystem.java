// added by JavaCIP
public interface FileSystem {

    public static FileSystem get(Configuration arg0) {
        return null;
    }

    public abstract UNKNOWN_74 getFileStatus(Path arg0);

    public abstract FileStatus[] listStatus(Path arg0);

    public abstract InputStream open(char arg0);
}
