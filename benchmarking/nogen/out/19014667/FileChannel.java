// added by JavaCIP
public interface FileChannel {

    public abstract boolean size();

    public abstract void transferFrom(FileChannel arg0, boolean arg1, boolean arg2);

    public abstract void transferFrom(FileChannel arg0, int arg1, boolean arg2);

    public abstract void close();
}
