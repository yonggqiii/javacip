// added by JavaCIP
public class CatalogRuntimeException extends RuntimeException {

    public static boolean UNPARSEABLE = false;

    public static boolean UNKNOWN_FORMAT = false;

    public boolean getRuntimeExceptionType() {
        return false;
    }

    CatalogRuntimeException() {
        super();
    }
}
