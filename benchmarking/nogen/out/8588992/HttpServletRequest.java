// added by JavaCIP
public interface HttpServletRequest {

    public abstract HttpSession getSession();

    public abstract String getParameter(String arg0);

    public abstract String[] getParameterValues(String arg0);
}
