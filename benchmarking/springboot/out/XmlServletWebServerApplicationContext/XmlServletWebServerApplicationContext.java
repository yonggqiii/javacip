public class XmlServletWebServerApplicationContext extends ServletWebServerApplicationContext {

    private final XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(this);

    public XmlServletWebServerApplicationContext() {
        this.reader.setEnvironment(JavaCIPUnknownScope.getEnvironment());
    }

    public XmlServletWebServerApplicationContext(Resource... resources) {
        load(resources);
        JavaCIPUnknownScope.refresh();
    }

    public XmlServletWebServerApplicationContext(String... resourceLocations) {
        load(resourceLocations);
        JavaCIPUnknownScope.refresh();
    }

    public XmlServletWebServerApplicationContext(Class<?> relativeClass, String... resourceNames) {
        load(relativeClass, resourceNames);
        JavaCIPUnknownScope.refresh();
    }

    public void setValidating(boolean validating) {
        this.reader.setValidating(validating);
    }

    public void setEnvironment(ConfigurableEnvironment environment) {
        super.setEnvironment(environment);
        this.reader.setEnvironment(JavaCIPUnknownScope.getEnvironment());
    }

    public final void load(Resource... resources) {
        this.reader.loadBeanDefinitions(resources);
    }

    public final void load(String... resourceLocations) {
        this.reader.loadBeanDefinitions(resourceLocations);
    }

    public final void load(Class<?> relativeClass, String... resourceNames) {
        Resource[] resources = new Resource[resourceNames.length];
        for (int i = 0; i < resourceNames.length; i++) {
            resources[i] = new ClassPathResource(resourceNames[i], relativeClass);
        }
        this.reader.loadBeanDefinitions(resources);
    }
}
