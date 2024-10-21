// added by JavaCIP
public interface FileSystem {

    public abstract FileStatus[] listStatus(Path arg0);

    public abstract OutputStream create(Path arg0, boolean arg1);

    public abstract InputStream open(Path arg0);

    public abstract boolean mkdirs(Path arg0);

    public abstract boolean isFile(Path arg0);

    public abstract boolean delete(Path arg0, boolean arg1);

    public abstract UNKNOWN_98 getFileStatus(Path arg0);
}
