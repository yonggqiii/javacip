// added by JavaCIP
public interface Session {

    public abstract Transaction beginTransaction();

    public abstract void save(NvUsers arg0);

    public abstract void flush();

    public abstract UNKNOWN_70 connection();
}
