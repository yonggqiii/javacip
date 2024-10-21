public class StandardReactiveWebEnvironment extends StandardEnvironment implements ConfigurableReactiveWebEnvironment {

    public StandardReactiveWebEnvironment() {
        super();
    }

    protected StandardReactiveWebEnvironment(MutablePropertySources propertySources) {
        super(propertySources);
    }
}
