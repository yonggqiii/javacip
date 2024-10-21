// added by JavaCIP
public interface HttpServletResponse {

    public static boolean SC_OK = false;

    public abstract PrintWriter getWriter();

    public abstract void setStatus(boolean arg0);
}
