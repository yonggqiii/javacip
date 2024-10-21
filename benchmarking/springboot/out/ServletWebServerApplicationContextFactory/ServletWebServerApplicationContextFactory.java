class ServletWebServerApplicationContextFactory implements ApplicationContextFactory {

    public Class<? extends ConfigurableEnvironment> getEnvironmentType(WebApplicationType webApplicationType) {
        return (webApplicationType != WebApplicationType.SERVLET) ? null : ApplicationServletEnvironment.class;
    }

    public ConfigurableEnvironment createEnvironment(WebApplicationType webApplicationType) {
        return (webApplicationType != WebApplicationType.SERVLET) ? null : new ApplicationServletEnvironment();
    }

    public ConfigurableApplicationContext create(WebApplicationType webApplicationType) {
        return (webApplicationType != WebApplicationType.SERVLET) ? null : createContext();
    }

    private ConfigurableApplicationContext createContext() {
        if (!AotDetector.useGeneratedArtifacts()) {
            return new AnnotationConfigServletWebServerApplicationContext();
        }
        return new ServletWebServerApplicationContext();
    }
}
