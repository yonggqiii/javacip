// added by JavaCIP
public interface RequestContext {

    public static RequestContext get() {
        return null;
    }

    public abstract boolean header(String arg0);

    public abstract boolean contextPath();
}
