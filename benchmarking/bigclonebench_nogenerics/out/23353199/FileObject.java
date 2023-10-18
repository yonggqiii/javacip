// added by JavaCIP
public interface FileObject {

    public abstract void createFolder();

    public abstract FileObject resolveFile(String arg0);

    public abstract boolean exists();

    public abstract UNKNOWN_96 getContent();

    public abstract void createFile();
}
