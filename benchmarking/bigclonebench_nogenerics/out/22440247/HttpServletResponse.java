// added by JavaCIP
public interface HttpServletResponse {

    public abstract void setContentType(String arg0);

    public abstract void setContentLength(int arg0);

    public abstract OutputStream getOutputStream();
}
