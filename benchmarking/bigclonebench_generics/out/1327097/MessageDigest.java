// added by JavaCIP
public interface MessageDigest {

    public static MessageDigest getInstance(String arg0) {
        return null;
    }

    public abstract void reset();

    public abstract byte[] digest(byte[] arg0);

    public abstract boolean digest();

    public abstract void update(byte[] arg0);
}