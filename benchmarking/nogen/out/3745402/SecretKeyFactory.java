// added by JavaCIP
public interface SecretKeyFactory {

    public static SecretKeyFactory getInstance(String arg0) {
        return null;
    }

    public abstract SecretKey generateSecret(PBEKeySpec arg0);
}
