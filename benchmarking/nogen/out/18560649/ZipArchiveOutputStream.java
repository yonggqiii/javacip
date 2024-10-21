// added by JavaCIP
public interface ZipArchiveOutputStream {

    public abstract ArchiveEntry createArchiveEntry(File arg0, boolean arg1);

    public abstract void putArchiveEntry(ArchiveEntry arg0);

    public abstract void closeArchiveEntry();
}
