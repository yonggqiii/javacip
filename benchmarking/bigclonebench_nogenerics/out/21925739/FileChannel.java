// added by JavaCIP
public interface FileChannel {

    public static UNKNOWN_76 MapMode = null;

    public abstract void read(ByteBuffer arg0);

    public abstract ByteBuffer map(boolean arg0, int arg1, int arg2);

    public abstract void position(int arg0);

    public abstract void close();

    public abstract void write(ByteBuffer arg0);
}
