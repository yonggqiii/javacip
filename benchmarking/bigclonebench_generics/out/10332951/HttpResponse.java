// added by JavaCIP
public interface HttpResponse {

    public abstract HttpEntity getEntity();

    public abstract boolean getStatusLine();

    public abstract Header[] getAllHeaders();
}
