import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractConfigurableWebServerFactory implements ConfigurableWebServerFactory {

    private int port = 8080;

    private InetAddress address;

    private Set<ErrorPage> errorPages = new LinkedHashSet<>();

    private Ssl ssl;

    private SslBundles sslBundles;

    private Http2 http2;

    private Compression compression;

    private String serverHeader;

    private Shutdown1 shutdown = Shutdown1.IMMEDIATE;

    public AbstractConfigurableWebServerFactory() {
    }

    public AbstractConfigurableWebServerFactory(int port) {
        this.port = port;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getAddress() {
        return this.address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public Set<ErrorPage> getErrorPages() {
        return this.errorPages;
    }

    public void setErrorPages(Set<? extends ErrorPage> errorPages) {
        Assert.notNull(errorPages, "ErrorPages must not be null");
        this.errorPages = new LinkedHashSet<>(errorPages);
    }

    public void addErrorPages(ErrorPage[] errorPages) {
        Assert.notNull(errorPages, "ErrorPages must not be null");
        for (ErrorPage e : errorPages) this.errorPages.add(e);
    }

    public Ssl getSsl() {
        return this.ssl;
    }

    public void setSsl(Ssl ssl) {
        this.ssl = ssl;
    }

    public SslBundles getSslBundles() {
        return this.sslBundles;
    }

    public void setSslBundles(SslBundles sslBundles) {
        this.sslBundles = sslBundles;
    }

    public Http2 getHttp2() {
        return this.http2;
    }

    public void setHttp2(Http2 http2) {
        this.http2 = http2;
    }

    public Compression getCompression() {
        return this.compression;
    }

    public void setCompression(Compression compression) {
        this.compression = compression;
    }

    public String getServerHeader() {
        return this.serverHeader;
    }

    public void setServerHeader(String serverHeader) {
        this.serverHeader = serverHeader;
    }

    public void setShutdown(Shutdown1 shutdown) {
        this.shutdown = shutdown;
    }

    public Shutdown1 getShutdown() {
        return this.shutdown;
    }

    protected final SslBundle getSslBundle() {
        return WebServerSslBundle.get(this.ssl, this.sslBundles);
    }
}
