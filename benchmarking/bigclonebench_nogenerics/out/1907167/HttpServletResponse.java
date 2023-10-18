// added by JavaCIP
public interface HttpServletResponse {

    public abstract void setContentLength(boolean arg0);

    public abstract void setContentType(boolean arg0);

    public abstract void addHeader(String arg0, boolean arg1);

    public abstract void addHeader(String arg0, String arg1);

    public abstract OutputStream getOutputStream();
}
