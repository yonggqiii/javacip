// added by JavaCIP
public interface SqlUpgradeXmlParser {

    public abstract List<String> getFilesForMilestones(int arg0, int arg1);

    public static SqlUpgradeXmlParser newParser() {
        return null;
    }

    public abstract boolean listMilestones();

    public abstract List<String> getViewsForMilestones(int arg0, int arg1);

    public abstract void parse(FileInputStream arg0);

    public abstract int getMilestoneIndex(String arg0);
}
