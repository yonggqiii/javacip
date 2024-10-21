class ReactiveWebServerApplicationContextFactory implements ApplicationContextFactory {

	@Override
	public Class<? extends ConfigurableEnvironment> getEnvironmentType(WebApplicationType webApplicationType) {
		return (webApplicationType != WebApplicationType.REACTIVE) ? null : ApplicationReactiveWebEnvironment.class;
	}

	@Override
	public ConfigurableEnvironment createEnvironment(WebApplicationType webApplicationType) {
		return (webApplicationType != WebApplicationType.REACTIVE) ? null : new ApplicationReactiveWebEnvironment();
	}

	@Override
	public ConfigurableApplicationContext create(WebApplicationType webApplicationType) {
		return (webApplicationType != WebApplicationType.REACTIVE) ? null : createContext();
	}

	private ConfigurableApplicationContext createContext() {
		if (!AotDetector.useGeneratedArtifacts()) {
			return new AnnotationConfigReactiveWebServerApplicationContext();
		}
		return new ReactiveWebServerApplicationContext();
	}

}
