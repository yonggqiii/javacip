class c20059828 {

    public synchronized AbstractBaseObject update(AbstractBaseObject obj) throws ApplicationRuntimeException {
        PreparedStatement preStat = null;
        StringBuffer sqlStat = new StringBuffer();
        MailSetting tmpMailSetting = (MailSetting) ((MailSetting) obj).clone();
        synchronized (JavaCIPUnknownScope.dbConn) {
            try {
                int updateCnt = 0;
                Timestamp currTime = Utility.getCurrentTimestamp();
                sqlStat.append("UPDATE MAIL_SETTING ");
                sqlStat.append("SET  USER_RECORD_ID=?, PROFILE_NAME=?, MAIL_SERVER_TYPE=?, DISPLAY_NAME=?, EMAIL_ADDRESS=?, REMEMBER_PWD_FLAG=?, SPA_LOGIN_FLAG=?, INCOMING_SERVER_HOST=?, INCOMING_SERVER_PORT=?, INCOMING_SERVER_LOGIN_NAME=?, INCOMING_SERVER_LOGIN_PWD=?, OUTGOING_SERVER_HOST=?, OUTGOING_SERVER_PORT=?, OUTGOING_SERVER_LOGIN_NAME=?, OUTGOING_SERVER_LOGIN_PWD=?, PARAMETER_1=?, PARAMETER_2=?, PARAMETER_3=?, PARAMETER_4=?, PARAMETER_5=?, UPDATE_COUNT=?, UPDATER_ID=?, UPDATE_DATE=? ");
                sqlStat.append("WHERE  ID=? AND UPDATE_COUNT=? ");
                preStat = JavaCIPUnknownScope.dbConn.prepareStatement(sqlStat.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                JavaCIPUnknownScope.setPrepareStatement(preStat, 1, tmpMailSetting.getUserRecordID());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 2, tmpMailSetting.getProfileName());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 3, tmpMailSetting.getMailServerType());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 4, tmpMailSetting.getDisplayName());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 5, tmpMailSetting.getEmailAddress());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 6, tmpMailSetting.getRememberPwdFlag());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 7, tmpMailSetting.getSpaLoginFlag());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 8, tmpMailSetting.getIncomingServerHost());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 9, tmpMailSetting.getIncomingServerPort());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 10, tmpMailSetting.getIncomingServerLoginName());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 11, tmpMailSetting.getIncomingServerLoginPwd());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 12, tmpMailSetting.getOutgoingServerHost());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 13, tmpMailSetting.getOutgoingServerPort());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 14, tmpMailSetting.getOutgoingServerLoginName());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 15, tmpMailSetting.getOutgoingServerLoginPwd());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 16, tmpMailSetting.getParameter1());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 17, tmpMailSetting.getParameter2());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 18, tmpMailSetting.getParameter3());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 19, tmpMailSetting.getParameter4());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 20, tmpMailSetting.getParameter5());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 21, new Integer(tmpMailSetting.getUpdateCount().intValue() + 1));
                JavaCIPUnknownScope.setPrepareStatement(preStat, 22, JavaCIPUnknownScope.sessionContainer.getUserRecordID());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 23, currTime);
                JavaCIPUnknownScope.setPrepareStatement(preStat, 24, tmpMailSetting.getID());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 25, tmpMailSetting.getUpdateCount());
                updateCnt = preStat.executeUpdate();
                JavaCIPUnknownScope.dbConn.commit();
                if (updateCnt == 0) {
                    throw new ApplicationRuntimeException(ErrorConstant.DB_CONCURRENT_ERROR);
                } else {
                    tmpMailSetting.setUpdaterID(JavaCIPUnknownScope.sessionContainer.getUserRecordID());
                    tmpMailSetting.setUpdateDate(currTime);
                    tmpMailSetting.setUpdateCount(new Integer(tmpMailSetting.getUpdateCount().intValue() + 1));
                    tmpMailSetting.setCreatorName(UserInfoFactory.getUserFullName(tmpMailSetting.getCreatorID()));
                    tmpMailSetting.setUpdaterName(UserInfoFactory.getUserFullName(tmpMailSetting.getUpdaterID()));
                    return (tmpMailSetting);
                }
            } catch (RuntimeException e) {
                try {
                    JavaCIPUnknownScope.dbConn.rollback();
                } catch (RuntimeException ex) {
                }
                JavaCIPUnknownScope.log.error(e, e);
                throw new ApplicationRuntimeException(ErrorConstant.DB_UPDATE_ERROR, e);
            } finally {
                try {
                    preStat.close();
                } catch (RuntimeException ignore) {
                } finally {
                    preStat = null;
                }
            }
        }
    }
}
