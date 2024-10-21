// added by JavaCIP
public interface Part {

    public static String ATTACHMENT = null;

    public abstract String getFileName();

    public abstract boolean isMimeType(String arg0);

    public abstract String getDisposition();

    public abstract InputStream getInputStream();
}
