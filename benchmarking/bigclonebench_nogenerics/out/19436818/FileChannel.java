// added by JavaCIP
public interface FileChannel {

    public abstract long size();

    public abstract void write(ByteBuffer arg0);

    public abstract void transferTo(long arg0, long arg1, FileChannel arg2);

    public abstract void close();
}
