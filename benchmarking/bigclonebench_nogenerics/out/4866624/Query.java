// added by JavaCIP
public interface Query {

    public abstract void setParameter(int arg0, boolean arg1);

    public abstract void setParameter(int arg0, String arg1);

    public abstract Usuaris getSingleResult();
}
