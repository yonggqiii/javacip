// added by JavaCIP
public interface ByteBuffer {

    public abstract char[] getChar();

    public static ByteBuffer allocate(int arg0) {
        return null;
    }

    public static boolean wrap(byte[] arg0) {
        return false;
    }

    public abstract void flip();

    public abstract boolean hasRemaining();
}
