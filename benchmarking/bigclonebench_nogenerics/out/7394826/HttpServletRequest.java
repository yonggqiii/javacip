// added by JavaCIP
public interface HttpServletRequest {

    public abstract String getParameter(String arg0);

    public abstract HttpSession getSession();

    public abstract void setAttribute(String arg0, Empresa arg1);

    public abstract void setAttribute(String arg0, boolean arg1);

    public abstract boolean getLocale();
}
