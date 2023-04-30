// added by JavaCIP
public interface JarFile {

    public abstract Enumeration<JarEntry> entries();

    public abstract InputStream getInputStream(ZipEntry arg0);
}
