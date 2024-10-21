class ApplicationReactiveWebEnvironment extends StandardReactiveWebEnvironment {

	@Override
	protected String doGetActiveProfilesProperty() {
		return null;
	}

	@Override
	protected String doGetDefaultProfilesProperty() {
		return null;
	}

	@Override
	protected ConfigurablePropertyResolver createPropertyResolver(MutablePropertySources propertySources) {
		return ConfigurationPropertySources.createPropertyResolver(propertySources);
	}

}
