public abstract class WebServerInitializedEvent extends ApplicationEvent {

    protected WebServerInitializedEvent(WebServer webServer) {
        super(webServer);
    }

    public WebServer getWebServer() {
        return getSource();
    }

    public abstract WebServerApplicationContext getApplicationContext();

    public WebServer getSource() {
        return (WebServer) super.getSource();
    }
}
