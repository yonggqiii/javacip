// added by JavaCIP
public interface URL {

    public abstract Object getProtocol();

    public abstract HttpURLConnection openConnection();

    public abstract InputStream openStream();
}
