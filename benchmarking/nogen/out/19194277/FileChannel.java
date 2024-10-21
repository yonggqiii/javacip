// added by JavaCIP
public interface FileChannel {

    public abstract FileChannel position(long arg0);

    public abstract void transferFrom(FileChannel arg0, int arg1, boolean arg2);

    public abstract void force(boolean arg0);

    public abstract void close();
}
