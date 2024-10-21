// added by JavaCIP
public interface ConfigurableApplicationContext {

    public abstract void addApplicationListener(ServerPortInfoApplicationContextInitializer arg0);

    public abstract ConfigurableEnvironment getEnvironment();
}
