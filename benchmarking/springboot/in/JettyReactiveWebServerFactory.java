import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class JettyReactiveWebServerFactory extends AbstractReactiveWebServerFactory
		implements ConfigurableJettyWebServerFactory {

	private static final Log logger = LogFactory.getLog(JettyReactiveWebServerFactory.class);

	private int acceptors = -1;

	private int selectors = -1;

	private boolean useForwardHeaders;

	private Set<JettyServerCustomizer> jettyServerCustomizers = new LinkedHashSet<>();

	private JettyResourceFactory resourceFactory;

	private ThreadPool threadPool;

	private int maxConnections = -1;

	public JettyReactiveWebServerFactory() {
	}

	public JettyReactiveWebServerFactory(int port) {
		super(port);
	}

	@Override
	public void setUseForwardHeaders(boolean useForwardHeaders) {
		this.useForwardHeaders = useForwardHeaders;
	}

	@Override
	public void setAcceptors(int acceptors) {
		this.acceptors = acceptors;
	}

	@Override
	public WebServer getWebServer(HttpHandler httpHandler) {
		JettyHttpHandlerAdapter servlet = new JettyHttpHandlerAdapter(httpHandler);
		Server server = createJettyServer(servlet);
		return new JettyWebServer(server, getPort() >= 0);
	}

	@Override
	public void addServerCustomizers(JettyServerCustomizer... customizers) {
		Assert.notNull(customizers, "Customizers must not be null");
		this.jettyServerCustomizers.addAll(Arrays.asList(customizers));
	}

	@Override
	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	public void setServerCustomizers(Collection<? extends JettyServerCustomizer> customizers) {
		Assert.notNull(customizers, "Customizers must not be null");
		this.jettyServerCustomizers = new LinkedHashSet<>(customizers);
	}

	public Collection<JettyServerCustomizer> getServerCustomizers() {
		return this.jettyServerCustomizers;
	}

	public ThreadPool getThreadPool() {
		return this.threadPool;
	}

	@Override
	public void setThreadPool(ThreadPool threadPool) {
		this.threadPool = threadPool;
	}

	@Override
	public void setSelectors(int selectors) {
		this.selectors = selectors;
	}

	public void setResourceFactory(JettyResourceFactory resourceFactory) {
		this.resourceFactory = resourceFactory;
	}

	protected JettyResourceFactory getResourceFactory() {
		return this.resourceFactory;
	}

	protected Server createJettyServer(JettyHttpHandlerAdapter servlet) {
		int port = Math.max(getPort(), 0);
		InetSocketAddress address = new InetSocketAddress(getAddress(), port);
		Server server = new Server(getThreadPool());
		server.addConnector(createConnector(address, server));
		server.setStopTimeout(0);
		ServletHolder servletHolder = new ServletHolder(servlet);
		servletHolder.setAsyncSupported(true);
		ServletContextHandler contextHandler = new ServletContextHandler("/", false, false);
		contextHandler.addServlet(servletHolder, "/");
		server.setHandler(addHandlerWrappers(contextHandler));
		JettyReactiveWebServerFactory.logger.info("Server initialized with port: " + port);
		if (this.maxConnections > -1) {
			server.addBean(new ConnectionLimit(this.maxConnections, server));
		}
		if (Ssl.isEnabled(getSsl())) {
			customizeSsl(server, address);
		}
		for (JettyServerCustomizer customizer : getServerCustomizers()) {
			customizer.customize(server);
		}
		if (this.useForwardHeaders) {
			new ForwardHeadersCustomizer().customize(server);
		}
		if (getShutdown() == Shutdown1.GRACEFUL) {
			StatisticsHandler statisticsHandler = new StatisticsHandler();
			statisticsHandler.setHandler(server.getHandler());
			server.setHandler(statisticsHandler);
		}
		server.setAttribute(WebServerFactory.class.getName(), getClass());
		return server;
	}

	private AbstractConnector createConnector(InetSocketAddress address, Server server) {
		HttpConfiguration httpConfiguration = new HttpConfiguration();
		httpConfiguration.setSendServerVersion(false);
		List<ConnectionFactory> connectionFactories = new ArrayList<>();
		connectionFactories.add(new HttpConnectionFactory(httpConfiguration));
		if (getHttp2() != null && getHttp2().isEnabled()) {
			connectionFactories.add(new HTTP2CServerConnectionFactory(httpConfiguration));
		}
		JettyResourceFactory resourceFactory = getResourceFactory();
		ServerConnector connector;
		if (resourceFactory != null) {
			connector = new ServerConnector(server, resourceFactory.getExecutor(), resourceFactory.getScheduler(),
					resourceFactory.getByteBufferPool(), this.acceptors, this.selectors,
					connectionFactories.toArray(new ConnectionFactory[0]));
		}
		else {
			connector = new ServerConnector(server, this.acceptors, this.selectors,
					connectionFactories.toArray(new ConnectionFactory[0]));
		}
		connector.setHost(address.getHostString());
		connector.setPort(address.getPort());
		return connector;
	}

	private Handler addHandlerWrappers(Handler handler) {
		if (getCompression() != null && getCompression().getEnabled()) {
			handler = applyWrapper(handler, JettyHandlerWrappers.createGzipHandlerWrapper(getCompression()));
		}
		if (StringUtils.hasText(getServerHeader())) {
			handler = applyWrapper(handler, JettyHandlerWrappers.createServerHeaderHandlerWrapper(getServerHeader()));
		}
		return handler;
	}

	private Handler applyWrapper(Handler handler, Handler.Wrapper wrapper) {
		wrapper.setHandler(handler);
		return wrapper;
	}

	private void customizeSsl(Server server, InetSocketAddress address) {
		new SslServerCustomizer(getHttp2(), address, getSsl().getClientAuth(), getSslBundle()).customize(server);
	}

}
