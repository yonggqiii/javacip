// added by JavaCIP
public interface User {

    public static Object anonymous() {
        return null;
    }

    public abstract boolean getActive();

    public abstract Object getPassword();
}
