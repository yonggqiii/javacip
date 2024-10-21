import java.util.LinkedHashMap;
import java.util.Map;

public abstract class DynamicRegistrationBean<D extends RegistrationDynamic> extends RegistrationBean
		implements BeanNameAware {

	private static final Log logger = LogFactory.getLog(RegistrationBean.class);

	private String name;

	private boolean asyncSupported = true;

	private Map<String, String> initParameters = new LinkedHashMap<>();

	private String beanName;

	private boolean ignoreRegistrationFailure;

	public void setName(String n) {
		Assert.hasLength(n, "Name must not be empty");
		this.name = n;
	}

	public void setAsyncSupported(boolean asyncSupported) {
		this.asyncSupported = asyncSupported;
	}

	public boolean isAsyncSupported() {
		return this.asyncSupported;
	}

	public void setInitParameters(Map<String, String> initParameters) {
		Assert.notNull(initParameters, "InitParameters must not be null");
		this.initParameters = new LinkedHashMap<>(initParameters);
	}

	public Map<String, String> getInitParameters() {
		return this.initParameters;
	}

	public void addInitParameter(String name, String value) {
		Assert.notNull(name, "Name must not be null");
		this.initParameters.put(name, value);
	}

	@Override
	protected final void register(String description, ServletContext servletContext) {
		D registration = addRegistration(description, servletContext);
		if (registration == null) {
			if (this.ignoreRegistrationFailure) {
				logger.info(StringUtils.capitalize(description) + " was not registered (possibly already registered?)");
				return;
			}
		}
		configure(registration);
	}

	public void setIgnoreRegistrationFailure(boolean ignoreRegistrationFailure) {
		this.ignoreRegistrationFailure = ignoreRegistrationFailure;
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	protected abstract D addRegistration(String description, ServletContext servletContext);

	protected void configure(D registration) {
		registration.setAsyncSupported(this.asyncSupported);
		if (!this.initParameters.isEmpty()) {
			registration.setInitParameters(this.initParameters);
		}
	}

	protected final String getOrDeduceName(Object value) {
		if (this.name != null) {
			return this.name;
		}
		if (this.beanName != null) {
			return this.beanName;
		}
		return Conventions.getVariableName(value);
	}

}
