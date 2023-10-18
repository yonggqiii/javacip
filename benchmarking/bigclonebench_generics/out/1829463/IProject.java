// added by JavaCIP
public interface IProject {

    public abstract void create(IProgressMonitor arg0);

    public abstract IProjectDescription getDescription();

    public abstract void open(IProgressMonitor arg0);

    public abstract boolean exists();

    public abstract IFolder getFolder(String arg0);

    public abstract void setDescription(IProjectDescription arg0, IProgressMonitor arg1);
}
