// added by JavaCIP
public interface ArchiveInputStream {

    public abstract ArchiveEntry getNextEntry();

    public abstract int read(byte[] arg0);

    public abstract void close();
}
