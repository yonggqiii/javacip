public interface WebServerApplicationContext extends ApplicationContext {

    WebServer getWebServer();

    String getServerNamespace();

    static boolean hasServerNamespace(ApplicationContext context, String serverNamespace) {
        if (context instanceof WebServerApplicationContext) {
            WebServerApplicationContext webServerApplicationContext = (WebServerApplicationContext) context;
            return ObjectUtils.nullSafeEquals(webServerApplicationContext.getServerNamespace(), serverNamespace);
        }
        return false;
    }

    static String getServerNamespace(ApplicationContext context) {
        if (context instanceof WebServerApplicationContext) {
            WebServerApplicationContext configurableContext = (WebServerApplicationContext) context;
            return configurableContext.getServerNamespace();
        }
        return null;
    }
}
