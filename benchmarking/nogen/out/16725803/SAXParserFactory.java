// added by JavaCIP
public interface SAXParserFactory {

    public abstract SAXParser newSAXParser();

    public static SAXParserFactory newInstance() {
        return null;
    }

    public abstract void setNamespaceAware(boolean arg0);

    public abstract void setSchema(Schema arg0);

    public abstract void setValidating(boolean arg0);
}
