class c1760353 {

    public static int deleteSysPosInsert() {
        Connection conn = null;
        PreparedStatement psmt = null;
        StringBuffer SQL = new StringBuffer(200);
        int deleted = 0;
        SQL.append(" DELETE FROM JHF_SYS_POSITION_INSERT ");
        try {
            conn = JdbcConnectionPool.mainConnection();
            conn.setAutoCommit(false);
            conn.setReadOnly(false);
            psmt = conn.prepareStatement(SQL.toString());
            deleted = psmt.executeUpdate();
            conn.commit();
        } catch (SQLRuntimeException e) {
            if (null != conn) {
                try {
                    conn.rollback();
                } catch (SQLRuntimeException e1) {
                    System.out.println(" error when roll back !");
                }
            }
        } finally {
            try {
                if (null != psmt) {
                    psmt.close();
                    psmt = null;
                }
                if (null != conn) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLRuntimeException e) {
                System.out.println(" error  when psmt close or conn close .");
            }
        }
        return deleted;
    }
}
