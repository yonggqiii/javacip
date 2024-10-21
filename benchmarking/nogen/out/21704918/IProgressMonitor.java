// added by JavaCIP
public interface IProgressMonitor {

    public abstract void setCanceled(boolean arg0);

    public abstract void worked(int arg0);

    public abstract void done();

    public abstract void beginTask(String arg0, int arg1);

    public abstract boolean isCanceled();
}
