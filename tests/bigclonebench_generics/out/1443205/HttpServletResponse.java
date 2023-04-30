// added by JavaCIP
public interface HttpServletResponse {

    public abstract void setHeader(String arg0, String arg1);

    public abstract void setStatus(int arg0);

    public abstract ServletOutputStream getOutputStream();
}
