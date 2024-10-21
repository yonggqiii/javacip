// added by JavaCIP
public interface MessageDigest {

    public static MessageDigest getInstance(String arg0) {
        return null;
    }

    public static boolean isEqual(byte[] arg0, boolean arg1) {
        return false;
    }

    public abstract byte[] digest(boolean arg0);

    public abstract void reset();

    public abstract void update(boolean arg0);

    public abstract void update(byte[] arg0);
}
