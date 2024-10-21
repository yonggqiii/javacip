public class GenericReactiveWebApplicationContext extends GenericApplicationContext
		implements ConfigurableReactiveWebApplicationContext {

	public GenericReactiveWebApplicationContext() {
	}

	public GenericReactiveWebApplicationContext(DefaultListableBeanFactory beanFactory) {
		super(beanFactory);
	}

	@Override
	protected ConfigurableEnvironment createEnvironment() {
		return new StandardReactiveWebEnvironment();
	}

	@Override
	protected Resource getResourceByPath(String path) {
		return new FilteredReactiveWebContextResource(path);
	}

}
