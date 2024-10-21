// added by JavaCIP
public interface HttpServletResponse {

    public abstract ServletOutputStream getOutputStream();

    public abstract void setContentType(String arg0);

    public abstract void setContentLength(int arg0);

    public abstract void setHeader(String arg0, String arg1);
}
