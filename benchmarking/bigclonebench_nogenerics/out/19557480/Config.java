// added by JavaCIP
public interface Config {

    public static Config loadConfig() {
        return null;
    }

    public abstract SimpleSocketAddress getLocalProxyServerAddress();
}
