// added by JavaCIP
public interface ByteBuffer {

    public static ByteBuffer allocateDirect(boolean arg0) {
        return null;
    }

    public abstract void flip();

    public abstract boolean hasRemaining();

    public abstract void clear();
}
