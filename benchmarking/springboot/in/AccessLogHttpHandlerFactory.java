import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

class AccessLogHttpHandlerFactory implements HttpHandlerFactory {

	private final File directory;

	private final String pattern;

	private final String prefix;

	private final String suffix;

	private final boolean rotate;

	AccessLogHttpHandlerFactory(File directory, String pattern, String prefix, String suffix, boolean rotate) {
		this.directory = directory;
		this.pattern = pattern;
		this.prefix = prefix;
		this.suffix = suffix;
		this.rotate = rotate;
	}

	@Override
	public HttpHandler getHandler(HttpHandler next) {
		try {
			createAccessLogDirectoryIfNecessary();
			XnioWorker worker = createWorker();
			String baseName = (this.prefix != null) ? this.prefix : "access_log.";
			String formatString = (this.pattern != null) ? this.pattern : "common";
			return new ClosableAccessLogHandler(next, worker,
					new DefaultAccessLogReceiver(worker, this.directory, baseName, this.suffix, this.rotate),
					formatString);
		}
		catch (RuntimeException ex) {
			throw new RuntimeException("Failed to create AccessLogHandler", ex);
		}
	}

	private void createAccessLogDirectoryIfNecessary() {
		Assert.state(this.directory != null, "Access log directory is not set");
		if (!this.directory.isDirectory() && !this.directory.mkdirs()) {
			throw new RuntimeException("Failed to create access log directory '" + this.directory + "'");
		}
	}

	private XnioWorker createWorker() {
		Xnio xnio = Xnio.getInstance(Undertow.class.getClassLoader());
		return xnio.createWorker(OptionMap.builder().set(Options.THREAD_DAEMON, true).getMap());
	}

}
