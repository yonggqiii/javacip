// added by JavaCIP
public class CatalogRuntimeException extends RuntimeException {

    public static boolean UNPARSEABLE;

    public static boolean UNKNOWN_FORMAT;

    public boolean getRuntimeExceptionType() {
        return false;
    }

    CatalogRuntimeException() {
        super();
    }
}
