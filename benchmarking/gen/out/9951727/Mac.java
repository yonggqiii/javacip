// added by JavaCIP
public interface Mac {

    public static Mac getInstance(String arg0) {
        return null;
    }

    public abstract void init(SecretKeySpec arg0);

    public abstract byte[] doFinal(byte[] arg0);
}
