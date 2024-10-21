// added by JavaCIP
public interface FileChannel {

    public abstract byte size();

    public abstract long transferFrom(FileChannel arg0, int arg1, byte arg2);

    public abstract void close();
}
