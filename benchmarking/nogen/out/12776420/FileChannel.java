// added by JavaCIP
public interface FileChannel {

    public abstract void position(byte arg0);

    public abstract ByteBuffer map(boolean arg0, byte arg1, int arg2);

    public abstract void close();
}
