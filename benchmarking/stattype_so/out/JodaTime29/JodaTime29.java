public class JodaTime29 {

    private static final int FIELD_COUNT = 10;

    public static void main(String[] args) {
    }

    public DateTime parseDateTime(final String text) {
        DateTimeFormatter[] formatters = new DateTimeFormatter[20];
        if (text.isEmpty())
            return null;
        int field = 0;
        DateTime dateTime = null;
        IllegalArgumentException exception = null;
        for (; field < FIELD_COUNT; ++field) {
            if (null != formatters[field]) {
                try {
                    dateTime = formatters[field].parseDateTime(text);
                    break;
                } catch (final IllegalArgumentException e) {
                    exception = null != exception ? exception : e;
                }
            }
        }
        if (dateTime == null) {
            throw exception;
        }
        return dateTime;
    }
}
