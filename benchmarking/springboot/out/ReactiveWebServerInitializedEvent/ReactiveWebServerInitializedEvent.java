public class ReactiveWebServerInitializedEvent extends WebServerInitializedEvent {

    private final ReactiveWebServerApplicationContext applicationContext;

    public ReactiveWebServerInitializedEvent(WebServer webServer, ReactiveWebServerApplicationContext applicationContext) {
        super(webServer);
        this.applicationContext = applicationContext;
    }

    public ReactiveWebServerApplicationContext getApplicationContext() {
        return this.applicationContext;
    }
}
