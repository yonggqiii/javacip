class c18458055 {

    public synchronized int executeCommand(Vector<String> pvStatement) throws Exception {
        int ret = 0, i = 0;
        Statement stmt = null;
        String temp = "";
        try {
            JavaCIPUnknownScope.oConexion.setAutoCommit(false);
            stmt = JavaCIPUnknownScope.oConexion.createStatement();
            for (i = 0; i < pvStatement.size(); i++) {
                temp = (String) pvStatement.elementAt(i);
                ret += stmt.executeUpdate(temp);
            }
            JavaCIPUnknownScope.oConexion.commit();
        } catch (SQLException e) {
            JavaCIPUnknownScope.oConexion.rollback();
            throw e;
        } finally {
            stmt.close();
            stmt = null;
        }
        return ret;
    }
}
