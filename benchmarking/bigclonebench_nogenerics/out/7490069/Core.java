// added by JavaCIP
public interface Core {

    public static Core getInstance() {
        return null;
    }

    public abstract boolean getString(String arg0);

    public abstract void showMessage(int arg0, boolean arg1, boolean arg2);

    public abstract void showMessage(int arg0, boolean arg1, String arg2);
}
