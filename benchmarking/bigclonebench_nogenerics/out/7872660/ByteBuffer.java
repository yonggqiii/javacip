// added by JavaCIP
public interface ByteBuffer {

    public abstract short getShort();

    public abstract void clear();

    public abstract void putInt(int arg0);

    public abstract void putInt(boolean arg0);

    public static ByteBuffer allocate(int arg0) {
        return null;
    }

    public abstract void flip();

    public abstract int getInt();

    public abstract void position(int arg0);

    public abstract byte get();
}
