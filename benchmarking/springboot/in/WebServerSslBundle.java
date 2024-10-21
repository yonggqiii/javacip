public final class WebServerSslBundle implements SslBundle {

	private final SslStoreBundle stores;

	private final SslBundleKey key;

	private final SslOptions options;

	private final String protocol;

	private final SslManagerBundle managers;

	private WebServerSslBundle(SslStoreBundle stores, String keyPassword, Ssl ssl) {
		this.stores = stores;
		this.key = SslBundleKey.of(keyPassword, ssl.getKeyAlias());
		this.protocol = ssl.getProtocol();
		this.options = SslOptions.of(ssl.getCiphers(), ssl.getEnabledProtocols());
		this.managers = SslManagerBundle.from(this.stores, this.key);
	}

	private static SslStoreBundle createPemKeyStoreBundle(Ssl ssl) {
		PemSslStoreDetails keyStoreDetails = new PemSslStoreDetails(ssl.getKeyStoreType(), ssl.getCertificate(),
				ssl.getCertificatePrivateKey())
			.withAlias(ssl.getKeyAlias());
		return new PemSslStoreBundle(keyStoreDetails, null);
	}

	private static SslStoreBundle createPemTrustStoreBundle(Ssl ssl) {
		PemSslStoreDetails trustStoreDetails = new PemSslStoreDetails(ssl.getTrustStoreType(),
				ssl.getTrustCertificate(), ssl.getTrustCertificatePrivateKey())
			.withAlias(ssl.getKeyAlias());
		return new PemSslStoreBundle(null, trustStoreDetails);
	}

	private static SslStoreBundle createJksKeyStoreBundle(Ssl ssl) {
		JksSslStoreDetails keyStoreDetails = new JksSslStoreDetails(ssl.getKeyStoreType(), ssl.getKeyStoreProvider(),
				ssl.getKeyStore(), ssl.getKeyStorePassword());
		return new JksSslStoreBundle(keyStoreDetails, null);
	}

	private static SslStoreBundle createJksTrustStoreBundle(Ssl ssl) {
		JksSslStoreDetails trustStoreDetails = new JksSslStoreDetails(ssl.getTrustStoreType(),
				ssl.getTrustStoreProvider(), ssl.getTrustStore(), ssl.getTrustStorePassword());
		return new JksSslStoreBundle(null, trustStoreDetails);
	}

	@Override
	public SslStoreBundle getStores() {
		return this.stores;
	}

	@Override
	public SslBundleKey getKey() {
		return this.key;
	}

	@Override
	public SslOptions getOptions() {
		return this.options;
	}

	@Override
	public String getProtocol() {
		return this.protocol;
	}

	@Override
	public SslManagerBundle getManagers() {
		return this.managers;
	}

	public static SslBundle get(Ssl ssl) throws NoSuchSslBundleException {
		return get(ssl, null);
	}

	public static SslBundle get(Ssl ssl, SslBundles sslBundles) throws NoSuchSslBundleException {
		Assert.state(Ssl.isEnabled(ssl), "SSL is not enabled");
		String keyPassword = ssl.getKeyPassword();
		String bundleName = ssl.getBundle();
		if (StringUtils.hasText(bundleName)) {
			return sslBundles.getBundle(bundleName);
		}
		SslStoreBundle stores = createStoreBundle(ssl);
		return new WebServerSslBundle(stores, keyPassword, ssl);
	}

	private static SslStoreBundle createStoreBundle(Ssl ssl) {
		KeyStore keyStore = createKeyStore(ssl);
		KeyStore trustStore = createTrustStore(ssl);
		return new WebServerSslStoreBundle(keyStore, trustStore, ssl.getKeyStorePassword());
	}

	private static KeyStore createKeyStore(Ssl ssl) {
		if (hasPemKeyStoreProperties(ssl)) {
			return createPemKeyStoreBundle(ssl).getKeyStore();
		}
		else if (hasJksKeyStoreProperties(ssl)) {
			return createJksKeyStoreBundle(ssl).getKeyStore();
		}
		return null;
	}

	private static KeyStore createTrustStore(Ssl ssl) {
		if (hasPemTrustStoreProperties(ssl)) {
			return createPemTrustStoreBundle(ssl).getTrustStore();
		}
		else if (hasJksTrustStoreProperties(ssl)) {
			return createJksTrustStoreBundle(ssl).getTrustStore();
		}
		return null;
	}

	private static boolean hasPemKeyStoreProperties(Ssl ssl) {
		return Ssl.isEnabled(ssl) && ssl.getCertificate() != null && ssl.getCertificatePrivateKey() != null;
	}

	private static boolean hasPemTrustStoreProperties(Ssl ssl) {
		return Ssl.isEnabled(ssl) && ssl.getTrustCertificate() != null;
	}

	private static boolean hasJksKeyStoreProperties(Ssl ssl) {
		return Ssl.isEnabled(ssl) && (ssl.getKeyStore() != null
				|| (ssl.getKeyStoreType() != null && ssl.getKeyStoreType().equals("PKCS11")));
	}

	private static boolean hasJksTrustStoreProperties(Ssl ssl) {
		return Ssl.isEnabled(ssl) && (ssl.getTrustStore() != null
				|| (ssl.getTrustStoreType() != null && ssl.getTrustStoreType().equals("PKCS11")));
	}

	@Override
	public String toString() {
		ToStringCreator creator = new ToStringCreator(this);
		creator.append("key", this.key);
		creator.append("protocol", this.protocol);
		creator.append("stores", this.stores);
		creator.append("options", this.options);
		return creator.toString();
	}

}
