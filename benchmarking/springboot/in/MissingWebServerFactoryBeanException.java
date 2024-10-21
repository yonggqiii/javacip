public class MissingWebServerFactoryBeanException extends NoSuchBeanDefinitionException {

	private final WebApplicationType webApplicationType;

	public MissingWebServerFactoryBeanException(
			Class<? extends WebServerApplicationContext> webServerApplicationContextClass,
			Class<? extends WebServerFactory> webServerFactoryClass, WebApplicationType webApplicationType) {
		super(webServerFactoryClass, String.format("Unable to start %s due to missing %s bean",
				webServerApplicationContextClass.getSimpleName(), webServerFactoryClass.getSimpleName()));
		this.webApplicationType = webApplicationType;
	}

	public WebApplicationType getWebApplicationType() {
		return this.webApplicationType;
	}

}
