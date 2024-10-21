// added by JavaCIP
public interface XmlPullParser {

    public static boolean END_DOCUMENT = false;

    public static boolean START_TAG = false;

    public abstract String getAttributeValue(Object arg0, String arg1);

    public abstract String getName();

    public abstract boolean nextText();

    public abstract boolean next();

    public abstract void setInput(InputStream arg0, Object arg1);

    public abstract boolean getEventType();
}
