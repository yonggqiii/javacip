// added by JavaCIP
public interface HttpServletResponse {

    public static boolean SC_NOT_FOUND = false;

    public abstract void setStatus(boolean arg0);

    public abstract void setContentType(String arg0);

    public abstract void setContentLength(int arg0);

    public abstract ServletOutputStream getOutputStream();
}
