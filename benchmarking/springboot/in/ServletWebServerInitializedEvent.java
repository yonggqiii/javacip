public class ServletWebServerInitializedEvent extends WebServerInitializedEvent {

	private final ServletWebServerApplicationContext applicationContext;

	public ServletWebServerInitializedEvent(WebServer webServer,
			ServletWebServerApplicationContext applicationContext) {
		super(webServer);
		this.applicationContext = applicationContext;
	}

	@Override
	public ServletWebServerApplicationContext getApplicationContext() {
		return this.applicationContext;
	}

}
