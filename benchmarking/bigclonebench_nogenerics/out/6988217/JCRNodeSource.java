// added by JavaCIP
public interface JCRNodeSource extends QueryResultSource {

    public abstract boolean exists();

    public abstract OutputStream getOutputStream();

    public abstract InputStream getInputStream();

    public abstract void delete();
}
