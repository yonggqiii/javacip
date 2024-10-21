// added by JavaCIP
public interface ApplicationContext extends ConfigurableApplicationContext {

    public abstract ConfigurableEnvironment getParent();
}
