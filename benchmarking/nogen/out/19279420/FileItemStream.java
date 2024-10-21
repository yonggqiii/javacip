// added by JavaCIP
public interface FileItemStream {

    public abstract String getFieldName();

    public abstract InputStream openStream();

    public abstract boolean isFormField();
}
