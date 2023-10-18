// added by JavaCIP
public interface ZipFile {

    public abstract Enumeration<ZipEntry> entries();

    public abstract InputStream getInputStream(ZipEntry arg0);
}
