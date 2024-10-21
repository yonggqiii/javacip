// added by JavaCIP
public interface HttpServletResponse {

    public static boolean SC_NOT_FOUND = false;

    public abstract void sendError(boolean arg0);

    public abstract void setContentType(boolean arg0);

    public abstract void setContentLength(boolean arg0);

    public abstract OutputStream getOutputStream();
}
