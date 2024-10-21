// added by JavaCIP
public interface FileChannel {

    public static UNKNOWN_175 MapMode = null;

    public abstract int size();

    public abstract ByteBuffer map(boolean arg0, int arg1, int arg2);

    public abstract void close();

    public abstract void write(ByteBuffer arg0);
}
