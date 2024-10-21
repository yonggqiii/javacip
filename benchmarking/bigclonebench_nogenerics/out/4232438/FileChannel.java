// added by JavaCIP
public interface FileChannel {

    public static UNKNOWN_75 MapMode = null;

    public abstract MappedByteBuffer map(boolean arg0, int arg1, boolean arg2);

    public abstract void close();

    public abstract void write(MappedByteBuffer arg0);
}
