// added by JavaCIP
public interface Xnio {

    public static Xnio getInstance(ClassLoader arg0) {
        return null;
    }

    public abstract XnioWorker createWorker(boolean arg0);
}
