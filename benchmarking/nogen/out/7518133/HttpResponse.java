// added by JavaCIP
public interface HttpResponse {

    public abstract HttpEntity getEntity();

    public abstract long getStatusLine();

    public abstract Header[] getAllHeaders();
}
