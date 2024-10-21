// added by JavaCIP
public interface PrimitiveLoader {

    public abstract boolean getBaseFolder();

    public abstract boolean getCompiledExtension();

    public abstract boolean getPrimitiveExtension();

    public abstract Savable loadResource(String arg0, boolean arg1);
}
