// added by JavaCIP
public interface FileChannel {

    public abstract void position(int arg0);

    public abstract byte read(ByteBuffer arg0);

    public abstract void write(ByteBuffer arg0);

    public abstract void close();
}
