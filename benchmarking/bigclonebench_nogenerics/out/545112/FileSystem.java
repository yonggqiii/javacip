// added by JavaCIP
public interface FileSystem {

    public static FileSystem get(boolean arg0, Configuration arg1) {
        return null;
    }

    public abstract boolean open(Path arg0);

    public abstract OutputStream create(Path arg0);
}
