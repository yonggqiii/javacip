class c16503018 {

    public String getNextObjectId() throws SQLRuntimeException {
        long nextserial = 1;
        String s0 = "lock table serials in exclusive mode";
        String s1 = "SELECT nextserial FROM serials WHERE tablename = 'SERVER_OIDS'";
        String s2;
        try {
            Statement stmt = JavaCIPUnknownScope.dbconnect.connection.createStatement();
            stmt.executeUpdate(s0);
            ResultSet rs = stmt.executeQuery(s1);
            if (!rs.next()) {
                s2 = "insert into serials (tablename,nextserial) values ('SERVER_OIDS', " + (nextserial) + ")";
            } else {
                nextserial = rs.getLong(1) + 1;
                s2 = "update serials set nextserial=" + (nextserial) + " where tablename='SERVER_OIDS'";
            }
            stmt.executeUpdate(s2);
            JavaCIPUnknownScope.dbconnect.connection.commit();
            rs.close();
            stmt.close();
            return "" + nextserial;
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.dbconnect.connection.rollback();
            throw e;
        }
    }
}
