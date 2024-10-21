class WebServerStartStopLifecycle implements SmartLifecycle {

    private final ServletWebServerApplicationContext applicationContext;

    private final WebServer webServer;

    private volatile boolean running;

    WebServerStartStopLifecycle(ServletWebServerApplicationContext applicationContext, WebServer webServer) {
        this.applicationContext = applicationContext;
        this.webServer = webServer;
    }

    public void start() {
        this.webServer.start();
        this.running = true;
        this.applicationContext.publishEvent(new ServletWebServerInitializedEvent(this.webServer, this.applicationContext));
    }

    public void stop() {
        this.running = false;
        this.webServer.stop();
    }

    public boolean isRunning() {
        return this.running;
    }

    public int getPhase() {
        return WebServerGracefulShutdownLifecycle.SMART_LIFECYCLE_PHASE - 1024;
    }
}
