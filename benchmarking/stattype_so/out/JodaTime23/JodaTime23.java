public class JodaTime23 {

    LocalDate getNearestDayOfWeek(LocalDate ld, String day) {
        LocalDate target = ld.dayOfWeek().setCopy(day);
        if (ld.getDayOfWeek() > DateTimeConstants.SATURDAY) {
            target = target.plusWeeks(1);
        }
        return target;
    }
}
