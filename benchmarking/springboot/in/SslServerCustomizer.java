import java.util.HashMap;
import java.util.Map;

public class SslServerCustomizer implements NettyServerCustomizer {

	private static final Log logger = LogFactory.getLog(SslServerCustomizer.class);

	private final Http2 http2;

	private final ClientAuth clientAuth;

	private volatile SslProvider sslProvider;

	private final Map<String, SslProvider> serverNameSslProviders;

	private volatile SslBundle sslBundle;

	public SslServerCustomizer(Http2 http2, Ssl.ClientAuth clientAuth, SslBundle sslBundle,
			Map<String, SslBundle> serverNameSslBundles) {
		this.http2 = http2;
		this.clientAuth = Ssl.ClientAuth.map(clientAuth, ClientAuth.NONE, ClientAuth.OPTIONAL, ClientAuth.REQUIRE);
		this.sslBundle = sslBundle;
		this.sslProvider = createSslProvider(sslBundle);
		this.serverNameSslProviders = createServerNameSslProviders(serverNameSslBundles);
	}

	private void applySecurity(SslContextSpec spec) {
		spec.sslContext(this.sslProvider.getSslContext());
	}

	void updateSslBundle(String serverName, SslBundle sslBundle) {
		logger.debug("SSL Bundle has been updated, reloading SSL configuration");
		if (serverName == null) {
			this.sslBundle = sslBundle;
			this.sslProvider = createSslProvider(sslBundle);
		}
		else {
			this.serverNameSslProviders.put(serverName, createSslProvider(sslBundle));
		}
	}

	private Map<String, SslProvider> createServerNameSslProviders(Map<String, SslBundle> serverNameSslBundles) {
		Map<String, SslProvider> serverNameSslProviders = new HashMap<>();
		return serverNameSslProviders;
	}

	private SslProvider createSslProvider(SslBundle sslBundle) {
		return SslProvider.builder().sslContext(createSslContextSpec(sslBundle)).build();
	}

	protected AbstractProtocolSslContextSpec<?> createSslContextSpec() {
		return createSslContextSpec(this.sslBundle);
	}

	protected final AbstractProtocolSslContextSpec<?> createSslContextSpec(SslBundle sslBundle) {
		AbstractProtocolSslContextSpec<?> sslContextSpec = (this.http2 != null && this.http2.isEnabled())
				? Http2SslContextSpec.forServer(sslBundle.getManagers().getKeyManagerFactory())
				: Http11SslContextSpec.forServer(sslBundle.getManagers().getKeyManagerFactory());
    return sslContextSpec;
    return null;
	}

}
