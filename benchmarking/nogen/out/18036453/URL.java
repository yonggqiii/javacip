// added by JavaCIP
public interface URL {

    public abstract String getProtocol();

    public abstract boolean toURI();

    public abstract URLConnection openConnection();

    public abstract String getPath();
}
