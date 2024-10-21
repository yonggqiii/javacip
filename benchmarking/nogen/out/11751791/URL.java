// added by JavaCIP
public interface URL {

    public abstract String getProtocol();

    public abstract int getPort();

    public abstract boolean getFile();

    public abstract HttpURLConnection openConnection();
}
