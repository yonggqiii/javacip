// added by JavaCIP
public interface URL {

    public abstract Object getProtocol();

    public abstract String toExternalForm();

    public abstract URLConnection openConnection();
}
