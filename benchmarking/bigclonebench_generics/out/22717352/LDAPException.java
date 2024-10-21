// added by JavaCIP
public class LDAPException extends RuntimeException {

    public static boolean OPERATIONS_ERROR = false;

    public static boolean OBJECT_CLASS_VIOLATION = false;

    public LDAPException(String arg0, boolean arg1, String arg2, SQLException arg3) {
        super();
    }

    public LDAPException(String arg0, boolean arg1, String arg2) {
        super();
    }

    LDAPException() {
        super();
    }
}
