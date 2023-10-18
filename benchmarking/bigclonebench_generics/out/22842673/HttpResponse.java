// added by JavaCIP
public interface HttpResponse {

    public abstract HttpStatus getStatusLine();

    public abstract HttpEntity getEntity();
}
