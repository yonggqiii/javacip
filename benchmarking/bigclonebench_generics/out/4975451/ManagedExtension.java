// added by JavaCIP
public interface ManagedExtension {

    public static ManagedExtension getOrCreate(int arg0, String arg1, String arg2) {
        return null;
    }

    public abstract String getLatestInstalledVersionBefore(int arg0);

    public abstract void addAndSelectVersion(int arg0);
}
