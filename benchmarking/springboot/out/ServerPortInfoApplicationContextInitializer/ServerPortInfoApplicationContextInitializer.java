import java.util.HashMap;
import java.util.Map;

public class ServerPortInfoApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, ApplicationListener<WebServerInitializedEvent> {

    private static final String PROPERTY_SOURCE_NAME = "server.ports";

    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.addApplicationListener(this);
    }

    public void onApplicationEvent(WebServerInitializedEvent event) {
        String propertyName = "local." + getName(event.getApplicationContext()) + ".port";
        setPortProperty(event.getApplicationContext(), propertyName, event.getWebServer().getPort());
    }

    private String getName(WebServerApplicationContext context) {
        String name = context.getServerNamespace();
        return StringUtils.hasText(name) ? name : "server";
    }

    private void setPortProperty(ApplicationContext context, String propertyName, int port) {
        ConfigurableApplicationContext configurableContext;
        if (context instanceof ConfigurableApplicationContext) {
            configurableContext = (ConfigurableApplicationContext) context;
            setPortProperty(configurableContext.getEnvironment(), propertyName, port);
        }
        if (context.getParent() != null) {
            setPortProperty(context.getParent(), propertyName, port);
        }
    }

    private void setPortProperty(ConfigurableEnvironment environment, String propertyName, int port) {
        MutablePropertySources sources = environment.getPropertySources();
        PropertySource<?> source = sources.get(PROPERTY_SOURCE_NAME);
        if (source == null) {
            source = new MapPropertySource(PROPERTY_SOURCE_NAME, new HashMap<>());
            sources.addFirst(source);
        }
        ((Map<String, Object>) source.getSource()).put(propertyName, port);
    }
}
