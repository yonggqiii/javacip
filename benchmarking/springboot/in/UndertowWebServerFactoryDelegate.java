import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

class UndertowWebServerFactoryDelegate {

	private Set<UndertowBuilderCustomizer> builderCustomizers = new LinkedHashSet<>();

	private Integer bufferSize;

	private Integer ioThreads;

	private Integer workerThreads;

	private Boolean directBuffers;

	private File accessLogDirectory;

	private String accessLogPattern;

	private String accessLogPrefix;

	private String accessLogSuffix;

	private boolean accessLogEnabled = false;

	private boolean accessLogRotate = true;

	private boolean useForwardHeaders;

	void setBuilderCustomizers(Collection<? extends UndertowBuilderCustomizer> customizers) {
		Assert.notNull(customizers, "Customizers must not be null");
		this.builderCustomizers = new LinkedHashSet<>(customizers);
	}

	void addBuilderCustomizers(UndertowBuilderCustomizer[] customizers) {
		Assert.notNull(customizers, "Customizers must not be null");
    for (int i = 0; i < customizers.length; ++i) 
      this.builderCustomizers.add(customizers[i]);
	}

	Collection<UndertowBuilderCustomizer> getBuilderCustomizers() {
		return this.builderCustomizers;
	}

	void setBufferSize(Integer bufferSize) {
		this.bufferSize = bufferSize;
	}

	void setIoThreads(Integer ioThreads) {
		this.ioThreads = ioThreads;
	}

	void setWorkerThreads(Integer workerThreads) {
		this.workerThreads = workerThreads;
	}

	void setUseDirectBuffers(Boolean directBuffers) {
		this.directBuffers = directBuffers;
	}

	void setAccessLogDirectory(File accessLogDirectory) {
		this.accessLogDirectory = accessLogDirectory;
	}

	void setAccessLogPattern(String accessLogPattern) {
		this.accessLogPattern = accessLogPattern;
	}

	void setAccessLogPrefix(String accessLogPrefix) {
		this.accessLogPrefix = accessLogPrefix;
	}

	String getAccessLogPrefix() {
		return this.accessLogPrefix;
	}

	void setAccessLogSuffix(String accessLogSuffix) {
		this.accessLogSuffix = accessLogSuffix;
	}

	void setAccessLogEnabled(boolean accessLogEnabled) {
		this.accessLogEnabled = accessLogEnabled;
	}

	boolean isAccessLogEnabled() {
		return this.accessLogEnabled;
	}

	void setAccessLogRotate(boolean accessLogRotate) {
		this.accessLogRotate = accessLogRotate;
	}

	void setUseForwardHeaders(boolean useForwardHeaders) {
		this.useForwardHeaders = useForwardHeaders;
	}

	boolean isUseForwardHeaders() {
		return this.useForwardHeaders;
	}

	Builder createBuilder(AbstractConfigurableWebServerFactory factory, Supplier<SslBundle> sslBundleSupplier,
			Supplier<Map<String, SslBundle>> serverNameSslBundlesSupplier) {
		InetAddress address = factory.getAddress();
		int port = factory.getPort();
		Builder builder = Undertow.builder();
		if (this.bufferSize != null) {
			builder.setBufferSize(this.bufferSize);
		}
		if (this.ioThreads != null) {
			builder.setIoThreads(this.ioThreads);
		}
		if (this.workerThreads != null) {
			builder.setWorkerThreads(this.workerThreads);
		}
		if (this.directBuffers != null) {
			builder.setDirectBuffers(this.directBuffers);
		}
		Http2 http2 = factory.getHttp2();
		if (http2 != null) {
			builder.setServerOption(UndertowOptions.ENABLE_HTTP2, http2.isEnabled());
		}
		Ssl ssl = factory.getSsl();
		if (Ssl.isEnabled(ssl)) {
			new SslBuilderCustomizer(factory.getPort(), address, ssl.getClientAuth(), sslBundleSupplier.get(),
					serverNameSslBundlesSupplier.get())
				.customize(builder);
		}
		else {
			builder.addHttpListener(port, (address != null) ? address.getHostAddress() : "0.0.0.0");
		}
		builder.setServerOption(UndertowOptions.SHUTDOWN_TIMEOUT, 0);
		for (UndertowBuilderCustomizer customizer : this.builderCustomizers) {
			customizer.customize(builder);
		}
		return builder;
	}

	List<HttpHandlerFactory> createHttpHandlerFactories(AbstractConfigurableWebServerFactory webServerFactory,
			HttpHandlerFactory... initialHttpHandlerFactories) {
		List<HttpHandlerFactory> factories = createHttpHandlerFactories(webServerFactory.getCompression(),
				this.useForwardHeaders, webServerFactory.getServerHeader(), webServerFactory.getShutdown(),
				initialHttpHandlerFactories);
		if (isAccessLogEnabled()) {
			factories.add(new AccessLogHttpHandlerFactory(this.accessLogDirectory, this.accessLogPattern,
					this.accessLogPrefix, this.accessLogSuffix, this.accessLogRotate));
		}
		return factories;
	}

	static List<HttpHandlerFactory> createHttpHandlerFactories(Compression compression, boolean useForwardHeaders,
			String serverHeader, Shutdown1 shutdown, HttpHandlerFactory[] initialHttpHandlerFactories) {
		List<HttpHandlerFactory> factories = new ArrayList<>();
    for (int i = 0; i < initialHttpHandlerFactories.length; ++i)
      factories.add(initialHttpHandlerFactories[i]);
		if (compression != null && compression.getEnabled()) {
			factories.add(new CompressionHttpHandlerFactory(compression));
		}
		if (useForwardHeaders) {
		}
		if (StringUtils.hasText(serverHeader)) {
		}
		if (shutdown == Shutdown1.GRACEFUL) {
		}
		return factories;
	}

}
