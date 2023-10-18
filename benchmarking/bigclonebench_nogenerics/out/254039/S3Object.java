// added by JavaCIP
public interface S3Object {

    public abstract String getKey();

    public abstract long getContentLength();

    public abstract InputStream getDataInputStream();

    public abstract void closeDataInputStream();
}
