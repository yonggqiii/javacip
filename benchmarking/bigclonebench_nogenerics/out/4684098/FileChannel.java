// added by JavaCIP
public interface FileChannel {

    public abstract boolean size();

    public abstract void transferTo(int arg0, boolean arg1, FileChannel arg2);

    public abstract void close();
}
