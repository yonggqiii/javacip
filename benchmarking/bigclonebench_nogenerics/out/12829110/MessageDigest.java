// added by JavaCIP
public interface MessageDigest {

    public abstract void reset();

    public abstract void update(byte[] arg0);

    public abstract Object digest();
}
