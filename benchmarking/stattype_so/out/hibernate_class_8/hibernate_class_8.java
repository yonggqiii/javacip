/**
 * Several models are date range sensitive, this base class provides that basic
 * functionality.
 *
 * @author jim
 */
public abstract class hibernate_class_8 {

    private static final long serialVersionUID = 1L;

    private Date from;

    private Date thru;

    /**
     * Determines if a model is active. A model is active if now is after or
     * equal to from , and thru is either null, or after now, or equal to now.
     */
    public boolean isActive() {
        Date now = new Date();
        boolean afterFrom = from.before(now) || from.equals(now);
        boolean beforeThru = thru == null || thru.after(now) || thru.equals(now);
        return afterFrom && beforeThru;
    }

    public boolean areDatesValid() {
        if (thru == null) {
            return true;
        } else {
            return thru.after(from);
        }
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getThru() {
        return thru;
    }

    public void setThru(Date thru) {
        this.thru = thru;
    }
}
