public abstract class AbstractReactiveWebServerFactory extends AbstractConfigurableWebServerFactory implements ConfigurableReactiveWebServerFactory {

    public AbstractReactiveWebServerFactory() {
    }

    public AbstractReactiveWebServerFactory(int port) {
        super(port);
    }
}
