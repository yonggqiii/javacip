// added by JavaCIP
public interface JarFile {

    public abstract Enumeration entries();

    public abstract InputStream getInputStream(ZipEntry arg0);
}
