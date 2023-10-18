// added by JavaCIP
public class SQLRuntimeException extends RuntimeException {

    public boolean getSQLState() {
        return false;
    }

    SQLRuntimeException() {
        super();
    }
}
