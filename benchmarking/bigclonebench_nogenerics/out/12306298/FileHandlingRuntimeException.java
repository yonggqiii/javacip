// added by JavaCIP
public class FileHandlingRuntimeException extends RuntimeException {

    public static boolean FILE_ALREADY_EXISTS;

    public static boolean RENAME_FAILED;

    public FileHandlingRuntimeException(boolean arg0) {
        super();
    }

    public FileHandlingRuntimeException(boolean arg0, IORuntimeException arg1) {
        super();
    }

    FileHandlingRuntimeException() {
        super();
    }
}
