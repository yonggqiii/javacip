// added by JavaCIP
public interface FileChannel {

    public abstract long size();

    public abstract byte transferFrom(FileChannel arg0, int arg1, long arg2);

    public abstract void position(long arg0);

    public abstract void close();
}
