// added by JavaCIP
public interface SslBundle {

    public abstract SslBundleKey getKey();

    public abstract SslStoreBundle getStores();

    public abstract SslOptions getOptions();

    public abstract boolean getProtocol();
}
