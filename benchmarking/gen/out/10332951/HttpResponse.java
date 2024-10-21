// added by JavaCIP
public interface HttpResponse {

    public abstract HttpEntity getEntity();

    public abstract int getStatusLine();

    public abstract Header[] getAllHeaders();
}
