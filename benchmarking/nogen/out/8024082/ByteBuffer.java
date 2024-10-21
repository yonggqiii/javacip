// added by JavaCIP
public interface ByteBuffer {

    public static ByteBuffer allocate(boolean arg0) {
        return null;
    }

    public static boolean wrap(byte[] arg0) {
        return false;
    }

    public abstract void flip();

    public abstract boolean hasRemaining();

    public abstract char get();
}
