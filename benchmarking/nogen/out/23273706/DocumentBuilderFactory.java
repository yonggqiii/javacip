// added by JavaCIP
public interface DocumentBuilderFactory {

    public static DocumentBuilderFactory newInstance() {
        return null;
    }

    public abstract void setValidating(boolean arg0);

    public abstract DocumentBuilder newDocumentBuilder();
}
