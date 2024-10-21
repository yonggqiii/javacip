// added by JavaCIP
public interface ArchiveInputStream {

    public abstract ZipArchiveEntry getNextEntry();

    public abstract void close();
}
