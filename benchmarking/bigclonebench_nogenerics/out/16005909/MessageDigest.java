// added by JavaCIP
public interface MessageDigest {

    public static MessageDigest getInstance(Object arg0) {
        return null;
    }

    public abstract void reset();

    public abstract byte[] digest(byte[] arg0);
}
