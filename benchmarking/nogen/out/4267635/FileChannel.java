// added by JavaCIP
public interface FileChannel {

    public abstract FileLock tryLock(int arg0, long arg1, boolean arg2);

    public abstract FileLock tryLock();

    public abstract long size();

    public abstract byte transferTo(long arg0, int arg1, FileChannel arg2);

    public abstract void close();
}
