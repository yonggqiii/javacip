class c20059827 {

    public synchronized AbstractBaseObject insert(AbstractBaseObject obj) throws ApplicationRuntimeException {
        PreparedStatement preStat = null;
        StringBuffer sqlStat = new StringBuffer();
        MailSetting tmpMailSetting = (MailSetting) ((MailSetting) obj).clone();
        synchronized (JavaCIPUnknownScope.dbConn) {
            try {
                Integer nextID = JavaCIPUnknownScope.getNextPrimaryID();
                Timestamp currTime = Utility.getCurrentTimestamp();
                sqlStat.append("INSERT ");
                sqlStat.append("INTO   MAIL_SETTING(ID, USER_RECORD_ID, PROFILE_NAME, MAIL_SERVER_TYPE, DISPLAY_NAME, EMAIL_ADDRESS, REMEMBER_PWD_FLAG, SPA_LOGIN_FLAG, INCOMING_SERVER_HOST, INCOMING_SERVER_PORT, INCOMING_SERVER_LOGIN_NAME, INCOMING_SERVER_LOGIN_PWD, OUTGOING_SERVER_HOST, OUTGOING_SERVER_PORT, OUTGOING_SERVER_LOGIN_NAME, OUTGOING_SERVER_LOGIN_PWD, PARAMETER_1, PARAMETER_2, PARAMETER_3, PARAMETER_4, PARAMETER_5, RECORD_STATUS, UPDATE_COUNT, CREATOR_ID, CREATE_DATE, UPDATER_ID, UPDATE_DATE) ");
                sqlStat.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
                preStat = JavaCIPUnknownScope.dbConn.prepareStatement(sqlStat.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                JavaCIPUnknownScope.setPrepareStatement(preStat, 1, nextID);
                JavaCIPUnknownScope.setPrepareStatement(preStat, 2, tmpMailSetting.getUserRecordID());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 3, tmpMailSetting.getProfileName());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 4, tmpMailSetting.getMailServerType());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 5, tmpMailSetting.getDisplayName());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 6, tmpMailSetting.getEmailAddress());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 7, tmpMailSetting.getRememberPwdFlag());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 8, tmpMailSetting.getSpaLoginFlag());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 9, tmpMailSetting.getIncomingServerHost());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 10, tmpMailSetting.getIncomingServerPort());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 11, tmpMailSetting.getIncomingServerLoginName());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 12, tmpMailSetting.getIncomingServerLoginPwd());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 13, tmpMailSetting.getOutgoingServerHost());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 14, tmpMailSetting.getOutgoingServerPort());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 15, tmpMailSetting.getOutgoingServerLoginName());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 16, tmpMailSetting.getOutgoingServerLoginPwd());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 17, tmpMailSetting.getParameter1());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 18, tmpMailSetting.getParameter2());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 19, tmpMailSetting.getParameter3());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 20, tmpMailSetting.getParameter4());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 21, tmpMailSetting.getParameter5());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 22, GlobalConstant.RECORD_STATUS_ACTIVE);
                JavaCIPUnknownScope.setPrepareStatement(preStat, 23, new Integer(0));
                JavaCIPUnknownScope.setPrepareStatement(preStat, 24, JavaCIPUnknownScope.sessionContainer.getUserRecordID());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 25, currTime);
                JavaCIPUnknownScope.setPrepareStatement(preStat, 26, JavaCIPUnknownScope.sessionContainer.getUserRecordID());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 27, currTime);
                preStat.executeUpdate();
                tmpMailSetting.setID(nextID);
                tmpMailSetting.setCreatorID(JavaCIPUnknownScope.sessionContainer.getUserRecordID());
                tmpMailSetting.setCreateDate(currTime);
                tmpMailSetting.setUpdaterID(JavaCIPUnknownScope.sessionContainer.getUserRecordID());
                tmpMailSetting.setUpdateDate(currTime);
                tmpMailSetting.setUpdateCount(new Integer(0));
                tmpMailSetting.setCreatorName(UserInfoFactory.getUserFullName(tmpMailSetting.getCreatorID()));
                tmpMailSetting.setUpdaterName(UserInfoFactory.getUserFullName(tmpMailSetting.getUpdaterID()));
                JavaCIPUnknownScope.dbConn.commit();
                return (tmpMailSetting);
            } catch (SQLRuntimeException sqle) {
                JavaCIPUnknownScope.log.error(sqle, sqle);
            } catch (RuntimeException e) {
                try {
                    JavaCIPUnknownScope.dbConn.rollback();
                } catch (RuntimeException ex) {
                }
                JavaCIPUnknownScope.log.error(e, e);
            } finally {
                try {
                    preStat.close();
                } catch (RuntimeException ignore) {
                } finally {
                    preStat = null;
                }
            }
            return null;
        }
    }
}
