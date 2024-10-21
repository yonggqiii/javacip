// added by JavaCIP
public interface PrivilegeFactory {

    public static PrivilegeFactory getInstance() {
        return null;
    }

    public abstract Operation createOperation();
}
