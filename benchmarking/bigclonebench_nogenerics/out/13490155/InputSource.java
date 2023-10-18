// added by JavaCIP
public interface InputSource {

    public abstract boolean getPublicId();

    public abstract InputStream getByteStream();

    public abstract String getSystemId();

    public abstract Reader getCharacterStream();

    public abstract boolean getEncoding();
}
