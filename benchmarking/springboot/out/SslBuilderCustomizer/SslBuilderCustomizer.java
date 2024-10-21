import java.net.InetAddress;
import java.util.Map;

class SslBuilderCustomizer implements UndertowBuilderCustomizer {

    private final int port;

    private final InetAddress address;

    private final ClientAuth clientAuth;

    private final SslBundle sslBundle;

    private final Map<String, SslBundle> serverNameSslBundles;

    SslBuilderCustomizer(int port, InetAddress address, ClientAuth clientAuth, SslBundle sslBundle, Map<String, SslBundle> serverNameSslBundles) {
        this.port = port;
        this.address = address;
        this.clientAuth = clientAuth;
        this.sslBundle = sslBundle;
        this.serverNameSslBundles = serverNameSslBundles;
    }

    public void customize(UndertowBuilder builder) {
        SslOptions options = this.sslBundle.getOptions();
        builder.addHttpsListener(this.port, getListenAddress(), createSslContext());
        builder.setSocketOption(Options.SSL_CLIENT_AUTH_MODE, ClientAuth.map(this.clientAuth, SslClientAuthMode.NOT_REQUESTED, SslClientAuthMode.REQUESTED, SslClientAuthMode.REQUIRED));
        if (options.getEnabledProtocols() != null) {
            builder.setSocketOption(Options.SSL_ENABLED_PROTOCOLS, Sequence.of(options.getEnabledProtocols()));
        }
        if (options.getCiphers() != null) {
            builder.setSocketOption(Options.SSL_ENABLED_CIPHER_SUITES, Sequence.of(options.getCiphers()));
        }
    }

    private SSLContext createSslContext() {
        SNIContextMatcherBuilder builder = new SNIContextMatcherBuilder();
        builder.setDefaultContext(this.sslBundle.createSslContext());
        return new SNISSLContext(builder.build());
    }

    private String getListenAddress() {
        if (this.address == null) {
            return "0.0.0.0";
        }
        return this.address.getHostAddress();
    }
}
