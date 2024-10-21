// added by JavaCIP
public interface HttpMethod {

    public abstract InputStream getResponseBodyAsStream();

    public abstract void releaseConnection();
}
