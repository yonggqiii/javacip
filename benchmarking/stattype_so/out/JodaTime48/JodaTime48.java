public class JodaTime48 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }

    private final MillisProvider millisProvider;

    private final DateTime someDate;

    public JodaTime48(MillisProvider millisProvider, DateTime someDate) {
        this.millisProvider = millisProvider;
        this.someDate = someDate;
    }

    public boolean isAvailable() {
        long now = millisProvider.getMillis();
        return (someDate.isBefore(now));
    }
}
