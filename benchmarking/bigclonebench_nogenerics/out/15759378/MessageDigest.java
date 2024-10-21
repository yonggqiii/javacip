// added by JavaCIP
public interface MessageDigest {

    public static MessageDigest getInstance(String arg0) {
        return null;
    }

    public abstract void reset();

    public abstract StringBuilder digest(byte[] arg0);
}
