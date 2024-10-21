// added by JavaCIP
public interface ByteBuffer {

    public abstract void clear();

    public static ByteBuffer allocate(int arg0) {
        return null;
    }

    public abstract void flip();

    public abstract void get(byte[] arg0);

    public abstract int limit();
}
