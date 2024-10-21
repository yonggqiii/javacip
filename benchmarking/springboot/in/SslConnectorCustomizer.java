import java.util.Map;

class SslConnectorCustomizer {

	private final Log logger;

	private final ClientAuth clientAuth;

	private final Connector connector;

	SslConnectorCustomizer(Log logger, Connector connector, ClientAuth clientAuth) {
		this.logger = logger;
		this.clientAuth = clientAuth;
		this.connector = connector;
	}

	void update(String serverName, SslBundle updatedSslBundle) {
		AbstractHttp11JsseProtocol<?> protocol = (AbstractHttp11JsseProtocol<?>) this.connector.getProtocolHandler();
		String host = (serverName != null) ? serverName : protocol.getDefaultSSLHostConfigName();
		this.logger.debug("SSL Bundle for host " + host + " has been updated, reloading SSL configuration");
		addSslHostConfig(protocol, host, updatedSslBundle);
	}

	void customize(SslBundle sslBundle, Map<String, SslBundle> serverNameSslBundles) {
		ProtocolHandler handler = this.connector.getProtocolHandler();
		Assert.state(handler instanceof AbstractHttp11JsseProtocol,
				"To use SSL, the connector's protocol handler must be an AbstractHttp11JsseProtocol subclass");
		configureSsl((AbstractHttp11JsseProtocol<?>) handler, sslBundle, serverNameSslBundles);
		this.connector.setScheme("https");
		this.connector.setSecure(true);
	}

	private void configureSsl(AbstractHttp11JsseProtocol<?> protocol, SslBundle sslBundle,
			Map<String, SslBundle> serverNameSslBundles) {
		protocol.setSSLEnabled(true);
		if (sslBundle != null) {
			addSslHostConfig(protocol, protocol.getDefaultSSLHostConfigName(), sslBundle);
		}
	}

	private void addSslHostConfig(AbstractHttp11JsseProtocol<?> protocol, String serverName, SslBundle sslBundle) {
		SSLHostConfig sslHostConfig = new SSLHostConfig();
		sslHostConfig.setHostName(serverName);
		configureSslClientAuth(sslHostConfig);
		applySslBundle(protocol, sslHostConfig, sslBundle);
		protocol.addSslHostConfig(sslHostConfig, true);
	}

	private void applySslBundle(AbstractHttp11JsseProtocol<?> protocol, SSLHostConfig sslHostConfig,
			SslBundle sslBundle) {
		SslBundleKey key = sslBundle.getKey();
		SslStoreBundle stores = sslBundle.getStores();
		SslOptions options = sslBundle.getOptions();
		sslHostConfig.setSslProtocol(sslBundle.getProtocol());
		SSLHostConfigCertificate certificate = new SSLHostConfigCertificate(sslHostConfig, Type.UNDEFINED);
		String keystorePassword = (stores.getKeyStorePassword() != null) ? stores.getKeyStorePassword() : "";
		certificate.setCertificateKeystorePassword(keystorePassword);
		if (key.getPassword() != null) {
			certificate.setCertificateKeyPassword(key.getPassword());
		}
		if (key.getAlias() != null) {
			certificate.setCertificateKeyAlias(key.getAlias());
		}
		sslHostConfig.addCertificate(certificate);
		if (options.getCiphers() != null) {
			String ciphers = StringUtils.arrayToCommaDelimitedString(options.getCiphers());
			sslHostConfig.setCiphers(ciphers);
		}
		configureSslStores(sslHostConfig, certificate, stores);
		configureEnabledProtocols(sslHostConfig, options);
	}

	private void configureEnabledProtocols(SSLHostConfig sslHostConfig, SslOptions options) {
		if (options.getEnabledProtocols() != null) {
			String enabledProtocols = StringUtils.arrayToDelimitedString(options.getEnabledProtocols(), "+");
			sslHostConfig.setProtocols(enabledProtocols);
		}
	}

	private void configureSslClientAuth(SSLHostConfig config) {
		config.setCertificateVerification(ClientAuth.map(this.clientAuth, "none", "optional", "required"));
	}

	private void configureSslStores(SSLHostConfig sslHostConfig, SSLHostConfigCertificate certificate,
			SslStoreBundle stores) {
		try {
			if (stores.getKeyStore() != null) {
				certificate.setCertificateKeystore(stores.getKeyStore());
			}
			if (stores.getTrustStore() != null) {
				sslHostConfig.setTrustStore(stores.getTrustStore());
			}
		}
		catch (RuntimeException ex) {
			throw new RuntimeException("Could not load store: " + ex.getMessage(), ex);
		}
	}

}
