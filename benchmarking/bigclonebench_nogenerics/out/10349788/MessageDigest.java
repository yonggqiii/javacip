// added by JavaCIP
public interface MessageDigest {

    public static MessageDigest getInstance(String arg0) {
        return null;
    }

    public abstract void update(byte[] arg0);

    public abstract boolean digest();

    public static boolean isEqual(byte[] arg0, boolean arg1) {
        return false;
    }
}
