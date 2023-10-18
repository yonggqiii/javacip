// added by JavaCIP
public interface Media {

    public abstract boolean getRating();

    public abstract boolean getPlayCount();

    public abstract void resetDirty();

    public abstract boolean isUserDirty();

    public abstract boolean getName();

    public abstract boolean isBaseDirty();

    public abstract boolean isContentDirty();

    public abstract boolean getID();
}
