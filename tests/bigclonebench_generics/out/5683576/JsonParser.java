// added by JavaCIP
public interface JsonParser {

    public abstract JsonToken nextToken();

    public abstract String getCurrentName();

    public abstract boolean getIntValue();

    public abstract boolean getText();
}
