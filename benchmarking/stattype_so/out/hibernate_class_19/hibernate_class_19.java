public class hibernate_class_19 {

    public final static String TABLE_NAME = "house";

    public final static String XDN = "xdn";

    public final static String DFN = "dfn";

    private long Id;

    private long xdn;

    private long dfn;

    private String address;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        this.Id = id;
    }

    public long getXdn() {
        return xdn;
    }

    public void setXdn(long xdn) {
        this.xdn = xdn;
    }

    public long getDfn() {
        return dfn;
    }

    public void setDfn(long dfn) {
        this.dfn = dfn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
