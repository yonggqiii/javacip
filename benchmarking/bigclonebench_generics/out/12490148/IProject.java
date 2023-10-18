// added by JavaCIP
public interface IProject {

    public abstract void create(SubProgressMonitor arg0);

    public abstract IProjectDescription getDescription();

    public abstract void open(SubProgressMonitor arg0);

    public abstract UNKNOWN_302 getFile(String arg0);

    public abstract boolean exists();

    public abstract void delete(boolean arg0, boolean arg1, SubProgressMonitor arg2);

    public abstract IFolder getFolder(String arg0);

    public abstract void setDescription(IProjectDescription arg0, SubProgressMonitor arg1);
}
