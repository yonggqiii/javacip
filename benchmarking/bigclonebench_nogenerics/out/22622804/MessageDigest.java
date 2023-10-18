// added by JavaCIP
public interface MessageDigest {

    public static MessageDigest getInstance(String arg0) {
        return null;
    }

    public abstract void update(boolean arg0, int arg1, boolean arg2);

    public abstract void update(byte[] arg0, int arg1, int arg2);

    public abstract byte[] digest();
}
