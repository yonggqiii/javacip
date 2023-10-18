// added by JavaCIP
public interface PageHelper {

    public static PageHelper createPageHelper(HttpServletRequest arg0, HttpServletResponse arg1) {
        return null;
    }

    public abstract void setSkipFlag(boolean arg0);

    public abstract String renderPlugins(WeblogEntryData arg0);
}
