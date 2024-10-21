// added by JavaCIP
public interface MessageDigest {

    public static MessageDigest getInstance(String arg0) {
        return null;
    }

    public abstract boolean isEqual(byte[] arg0, byte[] arg1);

    public abstract byte[] digest();

    public abstract void reset();

    public abstract void update(byte[] arg0);
}
