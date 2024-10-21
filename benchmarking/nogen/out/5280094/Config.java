// added by JavaCIP
public interface Config {

    public static Config getInstance() {
        return null;
    }

    public abstract SimpleSocketAddress getLocalProxyServerAddress();
}
