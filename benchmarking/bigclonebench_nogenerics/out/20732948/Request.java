// added by JavaCIP
public interface Request {

    public abstract HttpRequestBase buildHttpRequestBase(DefaultHttpClient arg0, Log arg1);

    public abstract HttpHost buildHttpHost(Log arg0);

    public abstract boolean getMethod();

    public abstract boolean getFinalUrl();
}
