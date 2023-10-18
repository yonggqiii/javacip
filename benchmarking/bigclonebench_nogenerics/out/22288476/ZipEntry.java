// added by JavaCIP
public interface ZipEntry {

    public abstract boolean getName();

    public abstract int getSize();

    public abstract boolean isDirectory();

    public abstract boolean getCompressedSize();
}
