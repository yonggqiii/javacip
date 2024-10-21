// added by JavaCIP
public interface FileChannel {

    public abstract Object size();

    public abstract int read(ByteBuffer arg0);

    public abstract void write(ByteBuffer arg0);

    public abstract void close();
}
