class c4593012 {

    private void upgradeSchema() throws IORuntimeException {
        Statement stmt = null;
        try {
            int i = JavaCIPUnknownScope.getSchema();
            JavaCIPUnknownScope.LOG.info("DB is currently at schema " + i);
            if (i < JavaCIPUnknownScope.SCHEMA_VERSION) {
                JavaCIPUnknownScope.LOG.info("Upgrading from schema " + i + " to schema " + JavaCIPUnknownScope.SCHEMA_VERSION);
                JavaCIPUnknownScope.conn.setAutoCommit(false);
                stmt = JavaCIPUnknownScope.conn.createStatement();
                while (i < JavaCIPUnknownScope.SCHEMA_VERSION) {
                    String qry;
                    switch(i) {
                        case 1:
                            qry = "UPDATE settings SET val = '2' WHERE var = 'schema'";
                            stmt.executeUpdate(qry);
                            break;
                    }
                    i++;
                }
                JavaCIPUnknownScope.conn.commit();
            }
        } catch (SQLRuntimeException e) {
            try {
                JavaCIPUnknownScope.conn.rollback();
            } catch (SQLRuntimeException e2) {
                JavaCIPUnknownScope.LOG.error(JavaCIPUnknownScope.SQL_ERROR, e2);
            }
            JavaCIPUnknownScope.LOG.fatal(JavaCIPUnknownScope.SQL_ERROR, e);
            throw new IORuntimeException("Error upgrading data store", e);
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                JavaCIPUnknownScope.conn.setAutoCommit(true);
            } catch (SQLRuntimeException e) {
                JavaCIPUnknownScope.LOG.error(JavaCIPUnknownScope.SQL_ERROR, e);
                throw new IORuntimeException("Unable to cleanup SQL resources", e);
            }
        }
    }
}
