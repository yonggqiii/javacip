public class DelegatingFilterProxyRegistrationBean extends AbstractFilterRegistrationBean<DelegatingFilterProxy>
		implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	private final String targetBeanName;

	public DelegatingFilterProxyRegistrationBean(String targetBeanName,
			ServletRegistrationBean<?>... servletRegistrationBeans) {
		super(servletRegistrationBeans);
		Assert.hasLength(targetBeanName, "TargetBeanName must not be null or empty");
		this.targetBeanName = targetBeanName;
		setName(targetBeanName);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	protected String getTargetBeanName() {
		return this.targetBeanName;
	}

	@Override
	public DelegatingFilterProxy getFilter() {
		return new DelegatingFilterProxy(this.targetBeanName, getWebApplicationContext()) {

			@Override
			protected void initFilterBean() throws ServletException {
			}

		};
	}

	private WebApplicationContext getWebApplicationContext() {
		Assert.notNull(this.applicationContext, "ApplicationContext be injected");
		Assert.isInstanceOf(WebApplicationContext.class, this.applicationContext);
		return (WebApplicationContext) this.applicationContext;
	}

}
