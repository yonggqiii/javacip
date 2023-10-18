// added by JavaCIP
public interface FileChannel {

    public abstract byte size();

    public abstract void transferTo(int arg0, int arg1, FileChannel arg2);

    public abstract void close();
}
