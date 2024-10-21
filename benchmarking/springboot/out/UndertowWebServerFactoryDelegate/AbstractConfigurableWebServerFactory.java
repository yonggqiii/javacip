// added by JavaCIP
public interface AbstractConfigurableWebServerFactory {

    public abstract java.net.InetAddress getAddress();

    public abstract Ssl getSsl();

    public abstract Compression getCompression();

    public abstract String getServerHeader();

    public abstract Http2 getHttp2();

    public abstract int getPort();

    public abstract Shutdown1 getShutdown();
}
