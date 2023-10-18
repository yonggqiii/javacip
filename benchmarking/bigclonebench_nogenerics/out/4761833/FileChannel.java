// added by JavaCIP
public interface FileChannel {

    public abstract double size();

    public abstract long transferFrom(FileChannel arg0, double arg1, double arg2);

    public abstract void close();

    public abstract void force(boolean arg0);
}
