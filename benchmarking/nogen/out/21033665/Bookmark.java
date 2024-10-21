// added by JavaCIP
public interface Bookmark {

    public abstract String isFavorite();

    public abstract void setId(long arg0);

    public abstract ColorLabel getColorLabel();

    public abstract boolean getPath();

    public abstract Object getName();

    public abstract Object getCommandText();

    public abstract Date getCreationTime();

    public abstract Date getLastAccess();

    public abstract String getUseCount();
}
