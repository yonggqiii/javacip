// added by JavaCIP
public interface IProgressMonitor {

    public abstract void beginTask(boolean arg0, int arg1);

    public abstract void worked(int arg0);

    public abstract void setTaskName(boolean arg0);

    public abstract void done();
}
