// added by JavaCIP
public interface ByteBuffer {

    public abstract void clear();

    public abstract void flip();

    public abstract void limit(int arg0);

    public static ByteBuffer allocate(int arg0) {
        return null;
    }

    public abstract byte[] array();
}
