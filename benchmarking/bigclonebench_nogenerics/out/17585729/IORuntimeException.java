// added by JavaCIP
public class IORuntimeException extends RuntimeException implements FileNotFoundRuntimeException {

    public void printStackTrace() {
    }

    public String getMessage() {
        return null;
    }

    IORuntimeException() {
        super();
    }
}
