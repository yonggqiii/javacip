// added by JavaCIP
public interface FileChannel {

    public static UNKNOWN_54 MapMode = null;

    public abstract long size();

    public abstract MappedByteBuffer map(boolean arg0, int arg1, long arg2);

    public abstract void transferTo(int arg0, long arg1, FileChannel arg2);

    public abstract void close();

    public abstract void write(MappedByteBuffer arg0);
}
