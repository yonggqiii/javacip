// added by JavaCIP
public interface FileChannel {

    public static UNKNOWN_141 MapMode = null;

    public abstract int size();

    public abstract MappedByteBuffer map(boolean arg0, int arg1, int arg2);

    public abstract void close();
}
