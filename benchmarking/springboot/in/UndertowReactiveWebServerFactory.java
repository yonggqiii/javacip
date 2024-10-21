import java.io.File;
import java.util.Collection;
import java.util.List;

public class UndertowReactiveWebServerFactory extends AbstractReactiveWebServerFactory
		implements ConfigurableUndertowWebServerFactory {

	private final UndertowWebServerFactoryDelegate delegate = new UndertowWebServerFactoryDelegate();

	public UndertowReactiveWebServerFactory() {
	}

	public UndertowReactiveWebServerFactory(int port) {
		super(port);
	}

	@Override
	public void setBuilderCustomizers(Collection<? extends UndertowBuilderCustomizer> customizers) {
		this.delegate.setBuilderCustomizers(customizers);
	}

	@Override
	public void addBuilderCustomizers(UndertowBuilderCustomizer... customizers) {
		this.delegate.addBuilderCustomizers(customizers);
	}

	public Collection<UndertowBuilderCustomizer> getBuilderCustomizers() {
		return this.delegate.getBuilderCustomizers();
	}

	@Override
	public void setBufferSize(Integer bufferSize) {
		this.delegate.setBufferSize(bufferSize);
	}

	@Override
	public void setIoThreads(Integer ioThreads) {
		this.delegate.setIoThreads(ioThreads);
	}

	@Override
	public void setWorkerThreads(Integer workerThreads) {
		this.delegate.setWorkerThreads(workerThreads);
	}

	@Override
	public void setUseDirectBuffers(Boolean directBuffers) {
		this.delegate.setUseDirectBuffers(directBuffers);
	}

	@Override
	public void setUseForwardHeaders(boolean useForwardHeaders) {
		this.delegate.setUseForwardHeaders(useForwardHeaders);
	}

	protected final boolean isUseForwardHeaders() {
		return this.delegate.isUseForwardHeaders();
	}

	@Override
	public void setAccessLogDirectory(File accessLogDirectory) {
		this.delegate.setAccessLogDirectory(accessLogDirectory);
	}

	@Override
	public void setAccessLogPattern(String accessLogPattern) {
		this.delegate.setAccessLogPattern(accessLogPattern);
	}

	@Override
	public void setAccessLogPrefix(String accessLogPrefix) {
		this.delegate.setAccessLogPrefix(accessLogPrefix);
	}

	@Override
	public void setAccessLogSuffix(String accessLogSuffix) {
		this.delegate.setAccessLogSuffix(accessLogSuffix);
	}

	public boolean isAccessLogEnabled() {
		return this.delegate.isAccessLogEnabled();
	}

	@Override
	public void setAccessLogEnabled(boolean accessLogEnabled) {
		this.delegate.setAccessLogEnabled(accessLogEnabled);
	}

	@Override
	public void setAccessLogRotate(boolean accessLogRotate) {
		this.delegate.setAccessLogRotate(accessLogRotate);
	}

}
