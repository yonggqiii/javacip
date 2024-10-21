public final class WebServerGracefulShutdownLifecycle implements SmartLifecycle {

	public static final int SMART_LIFECYCLE_PHASE = SmartLifecycle.DEFAULT_PHASE - 1024;

	private final WebServer webServer;

	private volatile boolean running;

	public WebServerGracefulShutdownLifecycle(WebServer webServer) {
		this.webServer = webServer;
	}

	@Override
	public void start() {
		this.running = true;
	}

	@Override
	public void stop() {
		throw new UnsupportedOperationException("Stop must not be invoked directly");
	}

	@Override
	public void stop(Runnable callback) {
		this.running = false;
	}

	@Override
	public boolean isRunning() {
		return this.running;
	}

	@Override
	public int getPhase() {
		return SMART_LIFECYCLE_PHASE;
	}

}
