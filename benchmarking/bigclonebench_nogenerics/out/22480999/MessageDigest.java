// added by JavaCIP
public interface MessageDigest {

    public static MessageDigest getInstance(String arg0, boolean arg1) {
        return null;
    }

    public abstract UNKNOWN_31 getProvider();

    public abstract void update(byte[] arg0);

    public abstract byte[] digest();
}
