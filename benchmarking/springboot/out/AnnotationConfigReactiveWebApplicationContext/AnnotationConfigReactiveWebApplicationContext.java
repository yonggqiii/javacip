public class AnnotationConfigReactiveWebApplicationContext extends AnnotationConfigApplicationContext implements ConfigurableReactiveWebApplicationContext {

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

    protected ConfigurableEnvironment createEnvironment() {
        return new StandardReactiveWebEnvironment();
    }

    protected Resource getResourceByPath(String path) {
        return new FilteredReactiveWebContextResource(path);
    }
}
