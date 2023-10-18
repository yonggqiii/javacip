// added by JavaCIP
public interface HttpResponse {

    public abstract boolean is2xxSuccess();

    public abstract UNKNOWN_61 getResponseHeaders();

    public abstract boolean getResponseBody();

    public abstract void close();
}
