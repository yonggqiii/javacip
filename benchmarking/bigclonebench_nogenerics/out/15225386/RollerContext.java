// added by JavaCIP
public interface RollerContext {

    public static RollerContext getRollerContext(HttpServletRequest arg0) {
        return null;
    }

    public abstract String createEntryPermalink(WeblogEntryData arg0, HttpServletRequest arg1, boolean arg2);
}
