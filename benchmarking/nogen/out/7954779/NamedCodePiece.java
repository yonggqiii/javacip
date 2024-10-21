// added by JavaCIP
public interface NamedCodePiece {

    public abstract byte getEndLine();

    public abstract byte getEndPosition();

    public abstract byte getStartPosition();

    public abstract void write(BufferedWriter arg0, Stack arg1, int arg2);

    public abstract byte getStartLine();
}
