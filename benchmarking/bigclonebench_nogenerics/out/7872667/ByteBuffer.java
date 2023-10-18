// added by JavaCIP
public interface ByteBuffer {

    public abstract void putShort(short arg0);

    public abstract short getShort();

    public abstract void clear();

    public static ByteBuffer allocate(int arg0) {
        return null;
    }

    public abstract void flip();

    public abstract int getInt();

    public abstract byte get();

    public abstract int capacity();

    public abstract void putFloat(float arg0);

    public abstract boolean hasRemaining();
}
