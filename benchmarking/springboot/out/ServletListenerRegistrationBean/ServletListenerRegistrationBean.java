import java.util.Collections;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

public class ServletListenerRegistrationBean<T extends EventListener> extends RegistrationBean {

    private static final Set<Class<?>> SUPPORTED_TYPES;

    static {
        Set<Class<?>> types = new HashSet<>();
        types.add(ServletContextAttributeListener.class);
        types.add(ServletRequestListener.class);
        types.add(ServletRequestAttributeListener.class);
        types.add(HttpSessionAttributeListener.class);
        types.add(HttpSessionIdListener.class);
        types.add(HttpSessionListener.class);
        types.add(ServletContextListener.class);
        SUPPORTED_TYPES = Collections.unmodifiableSet(types);
    }

    private T listener;

    public ServletListenerRegistrationBean() {
    }

    public ServletListenerRegistrationBean(T listener) {
        Assert.notNull(listener, "Listener must not be null");
        Assert.isTrue(isSupportedType(listener), "Listener is not of a supported type");
        this.listener = listener;
    }

    public void setListener(T listener) {
        Assert.notNull(listener, "Listener must not be null");
        Assert.isTrue(isSupportedType(listener), "Listener is not of a supported type");
        this.listener = listener;
    }

    public T getListener() {
        return this.listener;
    }

    protected String getDescription() {
        Assert.notNull(this.listener, "Listener must not be null");
        return "listener " + this.listener;
    }

    protected void register(String description, ServletContext servletContext) {
        try {
            servletContext.addListener(this.listener);
        } catch (RuntimeException ex) {
            throw new IllegalStateException("Failed to add listener '" + this.listener + "' to servlet context", ex);
        }
    }

    public static boolean isSupportedType(EventListener listener) {
        for (Class<?> type : SUPPORTED_TYPES) {
            if (ClassUtils.isAssignableValue(type, listener)) {
                return true;
            }
        }
        return false;
    }

    public static Set<Class<?>> getSupportedTypes() {
        return SUPPORTED_TYPES;
    }
}
