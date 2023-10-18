// added by JavaCIP
public interface MessageDigest {

    public static MessageDigest getInstance(String arg0) {
        return null;
    }

    public abstract void update(Object arg0, int arg1, int arg2);

    public abstract boolean digest();
}
