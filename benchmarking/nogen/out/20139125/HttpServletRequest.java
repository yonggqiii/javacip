// added by JavaCIP
public interface HttpServletRequest {

    public abstract boolean getMethod();

    public abstract Enumeration getHeaderNames();

    public abstract InputStream getInputStream();

    public abstract boolean getPathInfo();

    public abstract Object getQueryString();

    public abstract Enumeration getHeaders(String arg0);
}
