public class JodaTime19 {

    public static void main(String[] args) {
        Instant now = new Instant(new Date().getTime());
        Interval i2 = new Interval(now, (ReadableInstant) null);
        Interval i1 = new Interval(now, (ReadableInstant) null);
        boolean overlapping = i2.overlaps(i1);
    }
}
