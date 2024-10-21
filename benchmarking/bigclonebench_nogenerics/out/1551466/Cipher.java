// added by JavaCIP
public interface Cipher {

    public static boolean ENCRYPT_MODE = false;

    public static boolean DECRYPT_MODE = false;

    public static Cipher getInstance(String arg0) {
        return null;
    }

    public abstract void init(boolean arg0, PublicKey arg1);
}
