// added by JavaCIP
public interface MessageDigest {

    public static MessageDigest getInstance(boolean[] arg0) {
        return null;
    }

    public abstract void update(boolean arg0);

    public abstract byte[] digest();
}
