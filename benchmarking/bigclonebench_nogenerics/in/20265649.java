


class c20265649 {

    public static void setFinishedFlag(String ip, String port, String user, String dbname, String password, int flag) throws RuntimeException {
        String sql = "update flag set flag = " + flag;
        Connection conn = CubridDBCenter.getConnection(ip, port, dbname, user, password);
        System.out.println("====:::===" + ip);
        Statement stmt = null;
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            conn.commit();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            conn.rollback();
            throw ex;
        } finally {
            stmt.close();
            conn.close();
        }
    }

}
