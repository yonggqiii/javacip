// added by JavaCIP
public interface HttpServletResponse {

    public abstract void setStatus(boolean arg0);

    public abstract void addHeader(Object arg0, String arg1);

    public abstract OutputStream getOutputStream();
}
