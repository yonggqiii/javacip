public class UndertowServletWebServer extends UndertowWebServer {

	private final String contextPath;

	private final DeploymentManager manager;

	public UndertowServletWebServer(Builder builder, Iterable<HttpHandlerFactory> httpHandlerFactories,
			String contextPath, boolean autoStart) {
		super(builder, httpHandlerFactories, autoStart);
		this.contextPath = contextPath;
		this.manager = findManager(httpHandlerFactories);
	}

	private DeploymentManager findManager(Iterable<HttpHandlerFactory> httpHandlerFactories) {
		for (HttpHandlerFactory httpHandlerFactory : httpHandlerFactories) {
			if (httpHandlerFactory instanceof DeploymentManagerHttpHandlerFactory) {
				return ((DeploymentManagerHttpHandlerFactory) httpHandlerFactory).getDeploymentManager();
			}
		}
		return null;
	}

	@Override
	public HttpHandler createHttpHandler() {
		HttpHandler handler = super.createHttpHandler();
		if (StringUtils.hasLength(this.contextPath)) {
			handler = Handlers.path().addPrefixPath(this.contextPath, handler);
		}
		return handler;
	}

	@Override
	public String getStartLogMessage() {
		String contextPath = StringUtils.hasText(this.contextPath) ? this.contextPath : "/";
		StringBuilder message = new StringBuilder(super.getStartLogMessage());
		message.append(" with context path '");
		message.append(contextPath);
		message.append("'");
		return message.toString();
	}

	public DeploymentManager getDeploymentManager() {
		return this.manager;
	}

}
