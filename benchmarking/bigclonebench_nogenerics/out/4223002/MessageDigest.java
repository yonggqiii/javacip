// added by JavaCIP
public interface MessageDigest {

    public static MessageDigest getInstance(String arg0) {
        return null;
    }

    public abstract void reset();

    public abstract void update(boolean arg0);

    public abstract byte[] digest();
}