// added by JavaCIP
public interface TextToken {

    public static TextToken nextToken(PushbackInputStream arg0) {
        return null;
    }

    public abstract boolean isString();

    public abstract boolean getString();
}
