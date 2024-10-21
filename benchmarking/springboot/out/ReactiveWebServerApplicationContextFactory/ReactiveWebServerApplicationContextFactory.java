class ReactiveWebServerApplicationContextFactory implements ApplicationContextFactory {

    public Class<? extends ConfigurableEnvironment> getEnvironmentType(WebApplicationType webApplicationType) {
        return (webApplicationType != WebApplicationType.REACTIVE) ? null : ApplicationReactiveWebEnvironment.class;
    }

    public ConfigurableEnvironment createEnvironment(WebApplicationType webApplicationType) {
        return (webApplicationType != WebApplicationType.REACTIVE) ? null : new ApplicationReactiveWebEnvironment();
    }

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
