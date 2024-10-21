// added by JavaCIP
public interface DBOperation {

    public abstract Connection getConnection();

    public abstract void close();
}
