// added by JavaCIP
public interface XMLReader {

    public abstract void setEntityResolver(boolean arg0);

    public abstract void setErrorHandler(boolean arg0);

    public abstract void parse(InputSource arg0);

    public abstract void setContentHandler(boolean arg0);

    public abstract void setDTDHandler(boolean arg0);
}
