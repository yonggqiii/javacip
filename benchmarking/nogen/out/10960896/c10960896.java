class c10960896 {

    public static void executeUpdate(Database db, String... statements) throws SQLRuntimeException {
        Connection con = null;
        Statement stmt = null;
        try {
            con = JavaCIPUnknownScope.getConnection(db);
            con.setAutoCommit(false);
            stmt = con.createStatement();
            for (String statement : statements) {
                stmt.executeUpdate(statement);
            }
            con.commit();
        } catch (SQLRuntimeException e) {
            try {
                con.rollback();
            } catch (SQLRuntimeException e1) {
            }
            throw e;
        } finally {
            JavaCIPUnknownScope.closeStatement(stmt);
            JavaCIPUnknownScope.closeConnection(con);
        }
    }
}
