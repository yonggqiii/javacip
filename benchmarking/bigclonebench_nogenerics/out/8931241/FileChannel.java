// added by JavaCIP
public interface FileChannel {

    public abstract FileLock lock();

    public abstract int size();

    public abstract void transferTo(int arg0, int arg1, FileChannel arg2);

    public abstract void close();
}
