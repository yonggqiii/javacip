class c9251223 {

    public boolean addSiteScore(ArrayList<InitScoreTable> siteScores, InitScoreTable scoreTable, String filePath, String strTime) {
        boolean bResult = false;
        String strSql = "";
        Connection conn = null;
        Statement stm = null;
        try {
            conn = JavaCIPUnknownScope.db.getConnection();
            conn.setAutoCommit(false);
            stm = conn.createStatement();
            strSql = "delete from t_siteScore  where strTaskId = '" + scoreTable.getStrSiteScoreTaskId() + "'";
            stm.executeUpdate(strSql);
            for (int i = 0; i < siteScores.size(); i++) {
                InitScoreTable temp = siteScores.get(i);
                String tempSql = "select * from t_tagConf where strTagName='" + temp.getStrSiteScoreTagName() + "' and strTagYear='" + temp.getStrSiteScoreYear() + "' ";
                System.out.println(tempSql);
                ResultSet rst = stm.executeQuery(tempSql);
                if (rst.next()) {
                    temp.setStrSiteScoreTagId(rst.getString("strId"));
                    temp.setStrSiteinfoScoreParentId(rst.getString("strParentId"));
                }
                rst = null;
            }
            Iterator<InitScoreTable> it = siteScores.iterator();
            String strCreatedTime = JavaCIPUnknownScope.com.siteeval.common.Format.getDateTime();
            String taskId = "";
            while (it.hasNext()) {
                InitScoreTable thebean = it.next();
                taskId = thebean.getStrSiteScoreTaskId();
                String strId = JavaCIPUnknownScope.UID.getID();
                strSql = "INSERT INTO " + JavaCIPUnknownScope.strTableName3 + "(strId,strTaskId,strTagId," + "strTagType,strTagName,strParentId,flaTagScore,strYear,datCreatedTime,strCreator) " + "VALUES('" + strId + "','" + taskId + "','" + thebean.getStrSiteScoreTagId() + "','" + thebean.getStrSiteScoreTagType() + "','" + thebean.getStrSiteScoreTagName() + "','" + thebean.getStrSiteinfoScoreParentId() + "','" + thebean.getFlaSiteScoreTagScore() + "','" + thebean.getStrSiteScoreYear() + "','" + strCreatedTime + "','" + thebean.getStrSiteScoreCreator() + "')";
                stm.executeUpdate(strSql);
            }
            strSql = "update t_siteTotalScore set strSiteState=1,flaSiteScore='" + scoreTable.getFlaSiteScore() + "',flaInfoDisclosureScore='" + scoreTable.getFlaInfoDisclosureScore() + "',flaOnlineServicesScore='" + scoreTable.getFlaOnlineServicesScore() + "',flaPublicParticipationSore='" + scoreTable.getFlaPublicParticipationSore() + "',flaWebDesignScore='" + scoreTable.getFlaWebDesignScore() + "',strSiteFeature='" + scoreTable.getStrTotalScoreSiteFeature() + "',strSiteAdvantage='" + scoreTable.getStrTotalScoreSiteAdvantage() + "',strSiteFailure='" + scoreTable.getStrTotalScoreSiteFailure() + "' where strTaskId='" + scoreTable.getStrSiteScoreTaskId() + "'";
            stm.executeUpdate(strSql);
            strSql = "update " + JavaCIPUnknownScope.strTableName1 + " set templateUrl='" + filePath + "',dTaskBeginTime='" + strTime + "',dTaskEndTime='" + strTime + "' where strid = '" + scoreTable.getStrSiteScoreTaskId() + "'";
            stm.executeUpdate(strSql);
            conn.commit();
            bResult = true;
        } catch (RuntimeException e) {
            try {
                conn.rollback();
            } catch (RuntimeException eee) {
            }
            System.out.println("������վ���ֱ���Ϣʱ���?");
        } finally {
            try {
                conn.setAutoCommit(true);
                if (stm != null) {
                    stm.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (RuntimeException ee) {
            }
        }
        return bResult;
    }
}
