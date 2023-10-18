// added by JavaCIP
public interface MessageDigest {

    public static MessageDigest getInstance(boolean arg0) {
        return null;
    }

    public abstract void update(byte[] arg0);

    public abstract void update(byte arg0);

    public abstract byte[] digest();
}
