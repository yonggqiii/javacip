public interface ServletWebServerFactory extends WebServerFactory {

    WebServer getWebServer(ServletContextInitializer... initializers);
}
