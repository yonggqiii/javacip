// added by JavaCIP
public interface HttpServletResponse {

    public abstract void setStatus(int arg0);

    public abstract void addHeader(Object arg0, String arg1);

    public abstract OutputStream getOutputStream();
}
