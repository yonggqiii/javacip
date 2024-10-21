// added by JavaCIP
public interface XMLReader {

    public abstract void setContentHandler(ChannelFactory arg0);

    public abstract void setErrorHandler(ChannelFactory arg0);

    public abstract void parse(InputSource arg0);
}
