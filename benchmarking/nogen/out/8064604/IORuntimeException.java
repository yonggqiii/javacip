// added by JavaCIP
public class IORuntimeException extends RuntimeException implements ConnectIORuntimeException {

    public EOFRuntimeException getCause() {
        return null;
    }

    public IORuntimeException(String arg0) {
        super();
    }

    IORuntimeException() {
        super();
    }
}
