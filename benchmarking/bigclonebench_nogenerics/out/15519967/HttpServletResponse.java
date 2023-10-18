// added by JavaCIP
public interface HttpServletResponse {

    public abstract void setContentType(String arg0);

    public abstract void flushBuffer();

    public abstract void reset();

    public abstract void setHeader(String arg0, String arg1);

    public abstract void setContentLength(int arg0);

    public abstract ServletOutputStream getOutputStream();
}
