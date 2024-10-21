// added by JavaCIP
public interface PseudolocalizerArguments {

    public abstract String getVariant();

    public abstract List<String> getFileNames();

    public abstract String getType();

    public abstract PseudolocalizationPipeline getPipeline();

    public abstract boolean isInteractive();
}
