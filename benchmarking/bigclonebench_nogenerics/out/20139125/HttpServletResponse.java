// added by JavaCIP
public interface HttpServletResponse {

    public abstract void setStatus(int arg0);

    public abstract void addHeader(String arg0, String arg1);

    public abstract boolean getOutputStream();
}
