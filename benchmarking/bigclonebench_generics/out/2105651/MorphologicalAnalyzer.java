// added by JavaCIP
public interface MorphologicalAnalyzer {

    public static MorphologicalAnalyzer getInstance() {
        return null;
    }

    public abstract boolean isActive();

    public abstract ArrayList<ExtractedWord> extractWord(String arg0);
}
