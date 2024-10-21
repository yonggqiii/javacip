// added by JavaCIP
public interface Session {

    public abstract Transaction beginTransaction();

    public abstract Criteria createCriteria(Class<?> arg0);

    public abstract void close();
}
