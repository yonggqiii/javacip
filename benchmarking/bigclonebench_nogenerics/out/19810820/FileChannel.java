// added by JavaCIP
public interface FileChannel {

    public abstract long size();

    public abstract byte transferTo(int arg0, long arg1, FileChannel arg2);

    public abstract void close();
}
