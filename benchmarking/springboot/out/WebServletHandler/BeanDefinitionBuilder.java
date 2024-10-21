// added by JavaCIP
public interface BeanDefinitionBuilder {

    public static BeanDefinitionBuilder rootBeanDefinition(Class<ServletRegistrationBean> arg0) {
        return null;
    }

    public static BeanDefinitionBuilder rootBeanDefinition(Object arg0) {
        return null;
    }

    public abstract void addPropertyValue(String arg0, Object arg1);

    public abstract BeanDefinition getBeanDefinition();

    public abstract void addConstructorArgValue(Object arg0);
}
