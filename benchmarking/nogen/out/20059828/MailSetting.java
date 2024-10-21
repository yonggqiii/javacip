// added by JavaCIP
public interface MailSetting extends AbstractBaseObject {

    public abstract boolean getOutgoingServerHost();

    public abstract boolean getIncomingServerLoginName();

    public abstract boolean getUserRecordID();

    public abstract boolean getIncomingServerHost();

    public abstract boolean getParameter1();

    public abstract boolean getOutgoingServerLoginPwd();

    public abstract boolean getParameter2();

    public abstract boolean getIncomingServerPort();

    public abstract MailSetting clone();

    public abstract boolean getParameter3();

    public abstract boolean getOutgoingServerPort();

    public abstract boolean getRememberPwdFlag();

    public abstract boolean getParameter4();

    public abstract boolean getEmailAddress();

    public abstract boolean getUpdaterID();

    public abstract boolean getSpaLoginFlag();

    public abstract void setUpdaterID(boolean arg0);

    public abstract void setCreatorName(boolean arg0);

    public abstract UNKNOWN_265 getUpdateCount();

    public abstract boolean getIncomingServerLoginPwd();

    public abstract boolean getOutgoingServerLoginName();

    public abstract boolean getParameter5();

    public abstract boolean getCreatorID();

    public abstract void setUpdateDate(Timestamp arg0);

    public abstract boolean getDisplayName();

    public abstract boolean getProfileName();

    public abstract boolean getMailServerType();

    public abstract void setUpdaterName(boolean arg0);

    public abstract void setUpdateCount(Integer arg0);

    public abstract boolean getID();
}
