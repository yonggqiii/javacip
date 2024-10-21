public class ErrorPage {

	private final HttpStatus status;

	private final Class<? extends Throwable> exception;

	private final String path;

	public ErrorPage(String path) {
		this.status = null;
		this.exception = null;
		this.path = path;
	}

	public ErrorPage(HttpStatus status, String path) {
		this.status = status;
		this.exception = null;
		this.path = path;
	}

	public ErrorPage(Class<? extends Throwable> exception, String path) {
		this.status = null;
		this.exception = exception;
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}

	public Class<? extends Throwable> getException() {
		return this.exception;
	}

	public HttpStatus getStatus() {
		return this.status;
	}

	public int getStatusCode() {
		return (this.status != null) ? this.status.value() : 0;
	}

	public String getExceptionName() {
		return (this.exception != null) ? this.exception.getName() : null;
	}

	public boolean isGlobal() {
		return (this.status == null && this.exception == null);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof ErrorPage) {
      ErrorPage other = (ErrorPage) obj;
			return ObjectUtils.nullSafeEquals(getExceptionName(), other.getExceptionName())
					&& ObjectUtils.nullSafeEquals(this.path, other.path) && this.status == other.status;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ObjectUtils.nullSafeHashCode(getExceptionName());
		result = prime * result + ObjectUtils.nullSafeHashCode(this.path);
		result = prime * result + getStatusCode();
		return result;
	}

}
