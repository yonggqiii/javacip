// added by JavaCIP
public interface MapSource {

    public abstract int getMaxZoom();

    public abstract MapSpace getMapSpace();

    public abstract boolean getTileUrl(int arg0, int arg1, int arg2);
}
