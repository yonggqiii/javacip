class c13874343 {

    private static boolean execute(String query) throws SQLRuntimeException {
        boolean success = true;
        try {
            PreparedStatement stm = JavaCIPUnknownScope.con.prepareStatement(query);
            stm.executeUpdate();
            stm.close();
            JavaCIPUnknownScope.con.commit();
        } catch (SQLRuntimeException e) {
            try {
                JavaCIPUnknownScope.con.rollback();
            } catch (RuntimeException rbex) {
                rbex.printStackTrace();
            }
            success = false;
            throw e;
        }
        return success;
    }
}
