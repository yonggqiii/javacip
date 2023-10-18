// added by JavaCIP
public interface EOEditingContext {

    public abstract void lock();

    public abstract EOObjectStoreCoordinator rootObjectStore();

    public abstract void unlock();
}
