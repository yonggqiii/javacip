// added by JavaCIP
public interface Database {

    public abstract DBBroker getBroker();

    public abstract void release(DBBroker arg0);
}
