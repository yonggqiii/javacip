// added by JavaCIP
public interface Folder {

    public abstract boolean isEncrypted();

    public abstract Object getMd5Digest();

    public abstract void setMd5Digest(String arg0);

    public abstract void setEncrypted(boolean arg0);
}
