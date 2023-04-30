// added by JavaCIP
public interface IPackageFragmentRoot {

    public abstract boolean getPath();

    public abstract IPackageFragment getPackageFragment(String arg0);

    public abstract IPackageFragment createPackageFragment(String arg0, boolean arg1, IProgressMonitor arg2);
}
