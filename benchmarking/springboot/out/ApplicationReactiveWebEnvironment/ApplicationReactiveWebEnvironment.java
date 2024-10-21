class ApplicationReactiveWebEnvironment extends StandardReactiveWebEnvironment {

    protected String doGetActiveProfilesProperty() {
        return null;
    }

    protected String doGetDefaultProfilesProperty() {
        return null;
    }

    protected ConfigurablePropertyResolver createPropertyResolver(MutablePropertySources propertySources) {
        return ConfigurationPropertySources.createPropertyResolver(propertySources);
    }
}
