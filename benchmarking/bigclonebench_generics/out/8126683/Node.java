// added by JavaCIP
public interface Node extends ProcessingInstruction {

    public static boolean PROCESSING_INSTRUCTION_NODE = false;

    public abstract Node getNextSibling();

    public abstract boolean getNodeType();

    public abstract Object getNodeName();
}
