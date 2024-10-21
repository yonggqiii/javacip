public class AnnotationConfigReactiveWebApplicationContext extends AnnotationConfigApplicationContext
		implements ConfigurableReactiveWebApplicationContext {

	public AnnotationConfigReactiveWebApplicationContext() {
	}

	public AnnotationConfigReactiveWebApplicationContext(DefaultListableBeanFactory beanFactory) {
		super(beanFactory);
	}

	public AnnotationConfigReactiveWebApplicationContext(Class<?>... annotatedClasses) {
		super(annotatedClasses);
	}

	public AnnotationConfigReactiveWebApplicationContext(String... basePackages) {
		super(basePackages);
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
