// added by JavaCIP
public interface HttpServletResponse {

    public static boolean SC_FORBIDDEN = false;

    public abstract void setStatus(boolean arg0);

    public abstract ServletOutputStream getOutputStream();
}
