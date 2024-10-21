// added by JavaCIP
public interface HttpResponse {

    public abstract StatusLine getStatusLine();

    public abstract HttpEntity getEntity();

    public abstract Header getFirstHeader(String arg0);
}
