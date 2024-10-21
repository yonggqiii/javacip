// added by JavaCIP
public interface AbstractReader<A, B> {

    public static boolean FORMAT_DEP = false;

    public static boolean FORMAT_CONLLX = false;

    public abstract void setLanguage(boolean arg0);

    public abstract DepTree nextTree();
}
