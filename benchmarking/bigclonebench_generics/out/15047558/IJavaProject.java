// added by JavaCIP
public interface IJavaProject {

    public abstract boolean getRawClasspath();

    public abstract void setOutputLocation(Path arg0, NullProgressMonitor arg1);

    public abstract void setRawClasspath(boolean arg0, NullProgressMonitor arg1);
}
