// added by JavaCIP
public interface LoggerChannelListener {

    public abstract void messageLogged(boolean arg0, String arg1);

    public abstract void messageLogged(String arg0, RuntimeException arg1);
}
