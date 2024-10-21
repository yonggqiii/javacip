public interface WebServer {

	void start();

	void stop();

	int getPort();

	default void shutDownGracefully(GracefulShutdownCallback callback) {
		callback.shutdownComplete(GracefulShutdownResult.IMMEDIATE);
	}

	default void destroy() {
		stop();
	}

}
