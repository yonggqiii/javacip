// added by JavaCIP
public interface IProgressMonitor {

    public abstract void beginTask(boolean arg0, boolean arg1);

    public abstract boolean isCanceled();

    public abstract void setTaskName(boolean arg0);

    public abstract void worked(int arg0);
}
