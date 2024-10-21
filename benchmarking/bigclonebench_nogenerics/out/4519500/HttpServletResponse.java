// added by JavaCIP
public interface HttpServletResponse {

    public static boolean SC_NOT_FOUND = false;

    public abstract OutputStream getOutputStream();

    public abstract void sendError(boolean arg0);
}
