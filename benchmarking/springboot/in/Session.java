import java.io.File;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class Session {

	private Duration timeout = Duration.ofMinutes(30);

	private Set<SessionTrackingMode> trackingModes;

	private boolean persistent;

	private File storeDir;

	@NestedConfigurationProperty
	private final Cookie cookie = new Cookie();

	private final SessionStoreDirectory sessionStoreDirectory = new SessionStoreDirectory();

	public Duration getTimeout() {
		return this.timeout;
	}

	public void setTimeout(Duration timeout) {
		this.timeout = timeout;
	}

	public Set<SessionTrackingMode> getTrackingModes() {
		return this.trackingModes;
	}

	public void setTrackingModes(Set<SessionTrackingMode> trackingModes) {
		this.trackingModes = trackingModes;
	}

	public boolean isPersistent() {
		return this.persistent;
	}

	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}

	public File getStoreDir() {
		return this.storeDir;
	}

	public void setStoreDir(File storeDir) {
		this.sessionStoreDirectory.setDirectory(storeDir);
		this.storeDir = storeDir;
	}

	public Cookie getCookie() {
		return this.cookie;
	}

	SessionStoreDirectory getSessionStoreDirectory() {
		return this.sessionStoreDirectory;
	}

}
