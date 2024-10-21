// added by JavaCIP
public interface TarArchiveOutputStream {

    public static boolean LONGFILE_GNU = false;

    public abstract void setLongFileMode(boolean arg0);

    public abstract void putArchiveEntry(TarArchiveEntry arg0);

    public abstract void closeArchiveEntry();

    public abstract void close();
}
