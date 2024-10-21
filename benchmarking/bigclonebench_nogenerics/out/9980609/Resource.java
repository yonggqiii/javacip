// added by JavaCIP
public interface Resource {

    public abstract InputStream getInputStream();

    public abstract FileProvider as(Class<FileProvider> arg0);

    public abstract Touchable as(Object arg0);

    public abstract boolean getLastModified();
}
