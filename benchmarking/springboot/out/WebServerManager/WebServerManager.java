import java.util.function.Supplier;

class WebServerManager {

    private final ReactiveWebServerApplicationContext applicationContext;

    private final DelayedInitializationHttpHandler handler;

    private final WebServer webServer;

    WebServerManager(ReactiveWebServerApplicationContext applicationContext, ReactiveWebServerFactory factory, Supplier<HttpHandler> handlerSupplier, boolean lazyInit) {
        this.applicationContext = applicationContext;
        Assert.notNull(factory, "Factory must not be null");
        this.handler = new DelayedInitializationHttpHandler(handlerSupplier, lazyInit);
        this.webServer = factory.getWebServer(this.handler);
    }

    void start() {
        this.handler.initializeHandler();
        this.webServer.start();
        this.applicationContext.publishEvent(new ReactiveWebServerInitializedEvent(this.webServer, this.applicationContext));
    }

    void shutDownGracefully(GracefulShutdownCallback callback) {
        this.webServer.shutDownGracefully(callback);
    }

    void stop() {
        this.webServer.stop();
    }

    WebServer getWebServer() {
        return this.webServer;
    }

    HttpHandler getHandler() {
        return this.handler;
    }
}
