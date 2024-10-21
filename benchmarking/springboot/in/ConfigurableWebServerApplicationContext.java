public interface ConfigurableWebServerApplicationContext
		extends ConfigurableApplicationContext, WebServerApplicationContext {

	void setServerNamespace(String serverNamespace);

}
