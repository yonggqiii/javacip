// added by JavaCIP
public interface ByteBuffer {

    public abstract void getShort();

    public abstract void clear();

    public abstract void putInt(int arg0);

    public static ByteBuffer allocate(int arg0) {
        return null;
    }

    public abstract void flip();

    public abstract int getInt();

    public abstract byte get();

    public abstract int capacity();

    public abstract void putFloat(double arg0);

    public abstract boolean hasRemaining();
}
