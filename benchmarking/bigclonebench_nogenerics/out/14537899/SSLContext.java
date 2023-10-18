// added by JavaCIP
public interface SSLContext {

    public static SSLContext getInstance(boolean arg0) {
        return null;
    }

    public abstract void init(KeyManager[] arg0, TrustManager[] arg1, Object arg2);

    public abstract boolean getSocketFactory();
}
