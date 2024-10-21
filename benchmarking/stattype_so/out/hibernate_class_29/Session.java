// added by JavaCIP
public interface Session {

    public abstract <A> void delete(A arg0);

    public abstract <B> void saveOrUpdate(B arg0);

    public abstract Criteria createCriteria(java.lang.reflect.Type arg0);
}
