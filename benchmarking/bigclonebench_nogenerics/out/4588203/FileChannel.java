// added by JavaCIP
public interface FileChannel {

    public static UNKNOWN_86 MapMode = null;

    public abstract long size();

    public abstract MappedByteBuffer map(boolean arg0, int arg1, long arg2);

    public abstract void write(MappedByteBuffer arg0);

    public abstract void close();
}
