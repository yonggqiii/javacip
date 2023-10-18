// added by JavaCIP
public interface FileSystem {

    public abstract boolean isDirectory(Path arg0);

    public abstract boolean exists(Path arg0);

    public abstract FSDataOutputStream create(Path arg0);
}
