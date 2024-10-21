// added by JavaCIP
public interface IJavaProject {

    public abstract void setOutputLocation(String arg0, IProgressMonitor arg1);

    public abstract void setRawClasspath(IClasspathEntry[] arg0, Object arg1);

    public abstract IPackageFragmentRoot getPackageFragmentRoot(IFolder arg0);

    public abstract IClasspathEntry[] getRawClasspath();
}
