class c16761836 {

    protected synchronized AbstractBaseObject insert(AbstractBaseObject obj) throws ApplicationRuntimeException {
        PreparedStatement preStat = null;
        StringBuffer sqlStat = new StringBuffer();
        DmsRelationalWord tmpDmsRelationalWord = (DmsRelationalWord) ((DmsRelationalWord) obj).clone();
        synchronized (JavaCIPUnknownScope.dbConn) {
            try {
                Integer nextID = JavaCIPUnknownScope.getNextPrimaryID();
                Timestamp currTime = Utility.getCurrentTimestamp();
                sqlStat.append("INSERT ");
                sqlStat.append("INTO   DMS_RELATIONAL_WORD(ID, RECORD_STATUS, UPDATE_COUNT, CREATOR_ID, CREATE_DATE, UPDATER_ID, UPDATE_DATE, WORD, PARENT_ID, TYPE) ");
                sqlStat.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
                preStat = JavaCIPUnknownScope.dbConn.prepareStatement(sqlStat.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                JavaCIPUnknownScope.setPrepareStatement(preStat, 1, nextID);
                JavaCIPUnknownScope.setPrepareStatement(preStat, 2, tmpDmsRelationalWord.getRecordStatus());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 3, new Integer(0));
                JavaCIPUnknownScope.setPrepareStatement(preStat, 4, tmpDmsRelationalWord.getCreatorID());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 5, currTime);
                JavaCIPUnknownScope.setPrepareStatement(preStat, 6, tmpDmsRelationalWord.getUpdaterID());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 7, currTime);
                if (tmpDmsRelationalWord.getWord() == null || "".equals(tmpDmsRelationalWord.getWord().trim())) {
                    return null;
                }
                JavaCIPUnknownScope.setPrepareStatement(preStat, 8, tmpDmsRelationalWord.getWord());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 9, tmpDmsRelationalWord.getParentID());
                JavaCIPUnknownScope.setPrepareStatement(preStat, 10, tmpDmsRelationalWord.getType());
                preStat.executeUpdate();
                tmpDmsRelationalWord.setID(nextID);
                tmpDmsRelationalWord.setCreatorID(tmpDmsRelationalWord.getCreatorID());
                tmpDmsRelationalWord.setCreateDate(currTime);
                tmpDmsRelationalWord.setUpdaterID(tmpDmsRelationalWord.getUpdaterID());
                tmpDmsRelationalWord.setUpdateDate(currTime);
                tmpDmsRelationalWord.setUpdateCount(new Integer(0));
                tmpDmsRelationalWord.setCreatorName(UserInfoFactory.getUserFullName(tmpDmsRelationalWord.getCreatorID()));
                tmpDmsRelationalWord.setUpdaterName(UserInfoFactory.getUserFullName(tmpDmsRelationalWord.getUpdaterID()));
                JavaCIPUnknownScope.dbConn.commit();
                return (tmpDmsRelationalWord);
            } catch (RuntimeException e) {
                try {
                    JavaCIPUnknownScope.dbConn.rollback();
                } catch (RuntimeException ee) {
                }
                JavaCIPUnknownScope.log.error(e, e);
                throw new ApplicationRuntimeException(ErrorConstant.DB_INSERT_ERROR, e);
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
