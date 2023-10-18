// added by JavaCIP
public interface Connection {

    public abstract PreparedStatement prepareStatement(boolean arg0, boolean arg1);

    public abstract PreparedStatement prepareStatement(boolean arg0);

    public abstract void rollback();
}
