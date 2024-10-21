// added by JavaCIP
public interface HttpResponse {

    public abstract HttpEntity getEntity();

    public abstract float getStatusLine();

    public abstract Header[] getAllHeaders();
}
