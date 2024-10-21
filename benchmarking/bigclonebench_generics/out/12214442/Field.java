// added by JavaCIP
public interface Field {

    public static boolean IMAGE = false;

    public abstract boolean getType();

    public abstract FieldDataImage getDefault();

    public abstract boolean getName();

    public abstract void setID(int arg0);
}
