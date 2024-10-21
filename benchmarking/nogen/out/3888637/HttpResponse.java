// added by JavaCIP
public interface HttpResponse {

    public abstract HttpEntity getEntity();

    public abstract StatusLine getStatusLine();

    public abstract Header getFirstHeader(boolean arg0);
}
