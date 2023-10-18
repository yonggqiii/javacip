// added by JavaCIP
public interface FileChannel {

    public abstract boolean size();

    public abstract long transferFrom(FileChannel arg0, int arg1, boolean arg2);

    public abstract void transferFrom(FileChannel arg0, long arg1, boolean arg2);

    public abstract void close();
}
