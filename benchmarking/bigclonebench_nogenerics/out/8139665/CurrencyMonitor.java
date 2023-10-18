// added by JavaCIP
public interface CurrencyMonitor {

    public abstract String getCode();

    public abstract void setLastUpdateValue(BigDecimal arg0);

    public abstract void setLastUpdateTs(Date arg0);
}
