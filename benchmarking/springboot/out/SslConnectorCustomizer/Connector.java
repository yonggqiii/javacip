// added by JavaCIP
public interface Connector {

    public abstract ProtocolHandler getProtocolHandler();

    public abstract void setScheme(String arg0);

    public abstract void setSecure(boolean arg0);
}
