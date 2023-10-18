// added by JavaCIP
public interface ByteBuffer {

    public abstract void putShort(short arg0);

    public abstract boolean getShort();

    public abstract void clear();

    public abstract void putInt(int arg0);

    public static ByteBuffer allocate(int arg0) {
        return null;
    }

    public abstract boolean getFloat();

    public abstract void flip();

    public abstract boolean getInt();

    public abstract void putFloat(float arg0);

    public abstract boolean hasRemaining();
}
