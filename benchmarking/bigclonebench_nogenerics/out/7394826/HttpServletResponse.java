// added by JavaCIP
public interface HttpServletResponse {

    public abstract void setContentType(String arg0);

    public abstract PrintWriter getWriter();

    public abstract void sendRedirect(boolean arg0);

    public abstract void setHeader(String arg0, String arg1);

    public abstract ServletOutputStream getOutputStream();
}
