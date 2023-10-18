// added by JavaCIP
public interface TransformerFactory {

    public static TransformerFactory newInstance() {
        return null;
    }

    public abstract Transformer newTransformer(StreamSource arg0);
}
