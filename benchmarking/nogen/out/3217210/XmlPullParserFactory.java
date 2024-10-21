// added by JavaCIP
public interface XmlPullParserFactory {

    public static XmlPullParserFactory newInstance() {
        return null;
    }

    public abstract void setNamespaceAware(boolean arg0);

    public abstract XmlPullParser newPullParser();
}
