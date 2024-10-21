class JettyEmbeddedWebAppContext extends WebAppContext {

	JettyEmbeddedWebAppContext() {
		setHiddenClassMatcher(new ClassMatcher("org.springframework.boot.loader."));
	}

	@Override
	protected ServletHandler newServletHandler() {
		return new JettyEmbeddedServletHandler();
	}

	void deferredInitialize() throws RuntimeException {
		((JettyEmbeddedServletHandler) getServletHandler()).deferredInitialize();
	}

}
