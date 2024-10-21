import java.nio.charset.Charset;

class BasicAuthentication {

	private final String username;

	private final String password;

	private final Charset charset;

	BasicAuthentication(String username, String password, Charset charset) {
		Assert.notNull(username, "Username must not be null");
		Assert.notNull(password, "Password must not be null");
		this.username = username;
		this.password = password;
		this.charset = charset;
	}

	void applyTo(HttpHeaders headers) {
		if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
			headers.setBasicAuth(this.username, this.password, this.charset);
		}
	}

}
