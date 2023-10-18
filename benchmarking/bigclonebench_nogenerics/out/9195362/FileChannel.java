// added by JavaCIP
public interface FileChannel {

    public abstract long size();

    public abstract void transferTo(int arg0, long arg1, FileChannel arg2);

    public abstract void close();
}
