public interface ReactiveWebServerFactory extends WebServerFactory {

    WebServer getWebServer(HttpHandler httpHandler);
}
