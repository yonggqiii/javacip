// added by JavaCIP
public interface Resource {

    public abstract InputStream getInputStream();

    public abstract FileProvider as(Class<FileProvider> arg0);

    public abstract Touchable as(java.lang.reflect.GenericDeclaration arg0);

    public abstract boolean getLastModified();
}
