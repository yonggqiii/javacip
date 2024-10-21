// added by JavaCIP
public interface FileChannel {

    public abstract void read(ByteBuffer arg0);

    public abstract boolean size();

    public abstract void position(boolean arg0);

    public abstract void close();

    public abstract void write(boolean arg0);
}
