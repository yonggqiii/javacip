// added by JavaCIP
public interface XmlSerializer {

    public abstract void endTag(Object arg0, String arg1);

    public abstract void startTag(Object arg0, String arg1);

    public abstract void text(String arg0);

    public abstract void startDocument(String arg0, boolean arg1);

    public abstract void flush();

    public abstract void setOutput(boolean arg0, String arg1);
}
