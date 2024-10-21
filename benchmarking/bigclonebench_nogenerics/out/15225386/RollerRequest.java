// added by JavaCIP
public interface RollerRequest {

    public static boolean WEBLOGENTRYID_KEY = false;

    public static RollerRequest getRollerRequest(HttpServletRequest arg0) {
        return null;
    }

    public abstract boolean isUserAuthorizedToEdit();

    public abstract UNKNOWN_30302 getRoller();
}
