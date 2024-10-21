public class JodaTime26 {

    public static void main(String[] args) {
        Duration duration = new Duration(1328223198241L - 1326308781131L);
        Period p2 = new Period(duration.getMillis());
        // prints 0, should print 22 days
        System.out.println(p2.getDays());
        // prints  531 which is expected.}
        System.out.println(p2.getHours());
    }
}
