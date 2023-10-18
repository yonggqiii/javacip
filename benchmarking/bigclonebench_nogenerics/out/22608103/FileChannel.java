// added by JavaCIP
public interface FileChannel {

    public abstract long size();

    public abstract long transferFrom(FileChannel arg0, long arg1, long arg2);

    public abstract void close();
}
