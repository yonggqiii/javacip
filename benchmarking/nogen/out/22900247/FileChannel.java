// added by JavaCIP
public interface FileChannel {

    public abstract long size();

    public abstract byte read(ByteBuffer arg0);

    public abstract byte write(ByteBuffer arg0);

    public abstract void close();
}
