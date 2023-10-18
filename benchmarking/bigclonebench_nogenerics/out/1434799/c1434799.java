class c1434799 {

    public int executeBatch(String[] commands, String applicationid) throws RuntimeException {
        Statement statement = null;
        int errors = 0;
        int commandCount = 0;
        Connection conn = null;
        try {
            conn = JavaCIPUnknownScope.getConnection(applicationid);
            conn.setAutoCommit(false);
            statement = conn.createStatement();
            for (int i = 0; i < commands.length; i++) {
                String command = commands[i];
                if (command.trim().length() == 0) {
                    continue;
                }
                commandCount++;
                try {
                    JavaCIPUnknownScope.log.info("executing SQL: " + command);
                    int results = statement.executeUpdate(command);
                    JavaCIPUnknownScope.log.info("After execution, " + results + " row(s) have been changed");
                } catch (SQLRuntimeException ex) {
                    throw ex;
                }
            }
            conn.commit();
            JavaCIPUnknownScope.log.info("Executed " + commandCount + " SQL command(s) with " + errors + " error(s)");
        } catch (SQLRuntimeException ex) {
            if (conn != null) {
                conn.rollback();
            }
            throw ex;
        } catch (RuntimeException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            statement.close();
        }
        return errors;
    }
}
