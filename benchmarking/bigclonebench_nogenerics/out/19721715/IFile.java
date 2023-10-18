// added by JavaCIP
public interface IFile {

    public abstract boolean exists();

    public abstract void create(InputStream arg0, boolean arg1, IProgressMonitor arg2);

    public abstract UNKNOWN_86 getLocation();
}
