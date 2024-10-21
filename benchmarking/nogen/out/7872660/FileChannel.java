// added by JavaCIP
public interface FileChannel {

    public abstract int size();

    public abstract void read(ByteBuffer arg0);

    public abstract void write(ByteBuffer arg0);

    public abstract void close();
}
