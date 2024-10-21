public class GenericReactiveWebApplicationContext extends GenericApplicationContext implements ConfigurableReactiveWebApplicationContext {

    public GenericReactiveWebApplicationContext() {
    }

    public GenericReactiveWebApplicationContext(DefaultListableBeanFactory beanFactory) {
        super(beanFactory);
    }

    protected ConfigurableEnvironment createEnvironment() {
        return new StandardReactiveWebEnvironment();
    }

    protected Resource getResourceByPath(String path) {
        return new FilteredReactiveWebContextResource(path);
    }
}
