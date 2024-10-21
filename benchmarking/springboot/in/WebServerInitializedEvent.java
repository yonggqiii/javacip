public abstract class WebServerInitializedEvent extends ApplicationEvent {

	protected WebServerInitializedEvent(WebServer webServer) {
		super(webServer);
	}

	public WebServer getWebServer() {
		return getSource();
	}

	public abstract WebServerApplicationContext getApplicationContext();

	@Override
	public WebServer getSource() {
		return (WebServer) super.getSource();
	}

}
