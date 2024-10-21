public class ServletContextInitializerConfiguration extends AbstractConfiguration {

	private final ServletContextInitializer[] initializers;

	public ServletContextInitializerConfiguration(ServletContextInitializer... initializers) {
		super(new AbstractConfigurationBuilder());
		Assert.notNull(initializers, "Initializers must not be null");
		this.initializers = initializers;
	}

	@Override
	public void configure(WebAppContext context) throws RuntimeException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(context.getClassLoader());
		try {
			callInitializers(context);
		}
		finally {
			Thread.currentThread().setContextClassLoader(classLoader);
		}
	}

	private void callInitializers(WebAppContext context) {
		try {
			context.getContext().setExtendedListenerTypes(true);
			for (ServletContextInitializer initializer : this.initializers) {
				initializer.onStartup(context.getServletContext());
			}
		}
		finally {
			context.getContext().setExtendedListenerTypes(false);
		}
	}

}
