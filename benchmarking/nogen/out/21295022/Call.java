// added by JavaCIP
public interface Call {

    public abstract void setTargetEndpointAddress(URL arg0);

    public abstract void setPassword(String arg0);

    public abstract void setUsername(String arg0);

    public abstract Vector invoke(Object[] arg0);

    public abstract void setOperationName(String arg0);
}
