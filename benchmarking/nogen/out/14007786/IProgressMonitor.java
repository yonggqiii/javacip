// added by JavaCIP
public interface IProgressMonitor {

    public abstract void beginTask(String arg0, int arg1);

    public abstract void worked(int arg0);

    public abstract boolean isCanceled();
}
