// added by JavaCIP
public interface HttpServletResponse {

    public static boolean SC_REQUEST_ENTITY_TOO_LARGE = false;

    public static boolean SC_BAD_REQUEST = false;

    public static boolean SC_SERVICE_UNAVAILABLE = false;

    public static boolean SC_NOT_FOUND = false;

    public static boolean SC_OK = false;

    public static boolean SC_INTERNAL_SERVER_ERROR = false;

    public abstract void sendError(boolean arg0);

    public abstract void setStatus(boolean arg0);
}
