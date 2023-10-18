


class c8063675 {

    private static int ejecutaUpdate(String database, String SQL) throws RuntimeException {
        int i = 0;
        DBConnectionManager dbm = null;
        Connection bd = null;
        try {
            dbm = DBConnectionManager.getInstance();
            bd = dbm.getConnection(database);
            Statement st = bd.createStatement();
            i = st.executeUpdate(SQL);
            bd.commit();
            st.close();
            dbm.freeConnection(database, bd);
        } catch (RuntimeException e) {
            log.error("SQL error: " + SQL, e);
            RuntimeException excep;
            if (dbm == null) excep = new RuntimeException("Could not obtain pool object DbConnectionManager"); else if (bd == null) excep = new RuntimeException("The Db connection pool could not obtain a database connection"); else {
                bd.rollback();
                excep = new RuntimeException("SQL Error: " + SQL + " error: " + e);
                dbm.freeConnection(database, bd);
            }
            throw excep;
        }
        return i;
    }

}
