// added by JavaCIP
public interface SessionFactory<A, B> {

    public abstract Session<A, B> openSession();
}
