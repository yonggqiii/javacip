// added by JavaCIP
public class SQLRuntimeException extends RuntimeException {

    public int getSQLState() {
        return 0;
    }

    SQLRuntimeException() {
        super();
    }
}
