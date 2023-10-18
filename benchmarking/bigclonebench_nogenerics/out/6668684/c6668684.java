class c6668684 {

    public static void doIt(String action) {
        int f = -1;
        Statement s = null;
        Connection connection = null;
        try {
            JavaCIPUnknownScope.init();
            JavaCIPUnknownScope.log.info("<<< Looking up UserTransaction >>>");
            UserTransaction usertransaction = (UserTransaction) JavaCIPUnknownScope.context.lookup("java:comp/UserTransaction");
            JavaCIPUnknownScope.log.info("<<< beginning the transaction >>>");
            usertransaction.begin();
            JavaCIPUnknownScope.log.info("<<< Connecting to xadatasource >>>");
            connection = JavaCIPUnknownScope.xadatasource.getConnection();
            JavaCIPUnknownScope.log.info("<<< Connected >>>");
            s = connection.createStatement();
            s.executeUpdate("update testdata set foo=foo + 1 where id=1");
            if ((action != null) && action.equals("commit")) {
                JavaCIPUnknownScope.log.info("<<< committing the transaction >>>");
                usertransaction.commit();
            } else {
                JavaCIPUnknownScope.log.info("<<< rolling back the transaction >>>");
                usertransaction.rollback();
            }
            JavaCIPUnknownScope.log.info("<<< transaction complete >>>");
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.log.error("doIt", e);
        } finally {
            try {
                s.close();
                connection.close();
            } catch (RuntimeException x) {
                JavaCIPUnknownScope.log.error("problem closing statement/connection", x);
            }
        }
    }
}
