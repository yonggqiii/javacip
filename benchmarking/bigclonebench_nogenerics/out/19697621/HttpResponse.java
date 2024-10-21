// added by JavaCIP
public interface HttpResponse {

    public static boolean SC_OK = false;

    public abstract boolean getHttpStatusCode();

    public abstract String getResponseAsString();
}
