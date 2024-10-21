// added by JavaCIP
public interface FileChannel {

    public abstract int read(ByteBuffer arg0);

    public abstract byte size();

    public abstract byte position();

    public abstract void close();

    public abstract void write(ByteBuffer arg0);
}
