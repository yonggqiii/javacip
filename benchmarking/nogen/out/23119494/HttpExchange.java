// added by JavaCIP
public interface HttpExchange {

    public abstract Headers getResponseHeaders();

    public abstract Headers getRequestHeaders();

    public abstract boolean getResponseBody();

    public abstract boolean getRequestMethod();

    public abstract InputStream getRequestBody();

    public abstract boolean getProtocol();

    public abstract void sendResponseHeaders(int arg0, int arg1);

    public abstract UNKNOWN_63 getRemoteAddress();
}
