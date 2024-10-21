public class ServletContextApplicationContextInitializer
		implements ApplicationContextInitializer<ConfigurableWebApplicationContext>, Ordered {

	private int order = Ordered.HIGHEST_PRECEDENCE;

	private final ServletContext servletContext;

	private final boolean addApplicationContextAttribute;

	public ServletContextApplicationContextInitializer(ServletContext servletContext) {
		this(servletContext, false);
	}

	public ServletContextApplicationContextInitializer(ServletContext servletContext,
			boolean addApplicationContextAttribute) {
		this.servletContext = servletContext;
		this.addApplicationContextAttribute = addApplicationContextAttribute;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public int getOrder() {
		return this.order;
	}

	@Override
	public void initialize(ConfigurableWebApplicationContext applicationContext) {
		applicationContext.setServletContext(this.servletContext);
		if (this.addApplicationContextAttribute) {
			this.servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
					applicationContext);
		}

	}

}
