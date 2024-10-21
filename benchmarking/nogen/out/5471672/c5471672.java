class c5471672 {

    private void upgradeSchema() throws IORuntimeException {
        Statement stmt = null;
        try {
            int i = JavaCIPUnknownScope.getSchema();
            if (i < JavaCIPUnknownScope.SCHEMA_VERSION) {
                JavaCIPUnknownScope.conn.setAutoCommit(false);
                stmt = JavaCIPUnknownScope.conn.createStatement();
                while (i < JavaCIPUnknownScope.SCHEMA_VERSION) {
                    String qry;
                    switch(i) {
                        case 1:
                            qry = "CREATE TABLE log (id INTEGER PRIMARY KEY, context VARCHAR(16) NOT NULL, level VARCHAR(16) NOT NULL, time LONG INT NOT NULL, msg LONG VARCHAR NOT NULL, parent INT)";
                            stmt.executeUpdate(qry);
                            qry = "UPDATE settings SET val = '2' WHERE var = 'schema'";
                            stmt.executeUpdate(qry);
                            break;
                        case 2:
                            qry = "CREATE TABLE monitor (id INTEGER PRIMARY KEY NOT NULL, status INTEGER NOT NULL)";
                            stmt.executeUpdate(qry);
                            qry = "UPDATE settings SET val = '3' WHERE var = 'schema'";
                            stmt.executeUpdate(qry);
                            break;
                        case 3:
                            qry = "CREATE TABLE favs (id INTEGER PRIMARY KEY NOT NULL)";
                            stmt.executeUpdate(qry);
                            qry = "UPDATE settings SET val = '4' WHERE var = 'schema'";
                            stmt.executeUpdate(qry);
                            break;
                        case 4:
                            qry = "DROP TABLE log";
                            stmt.executeUpdate(qry);
                            qry = "UPDATE settings SET val = '5' WHERE var = 'schema'";
                            stmt.executeUpdate(qry);
                            break;
                        case 5:
                            qry = "UPDATE settings SET val = '120000' WHERE var = 'SleepTime'";
                            stmt.executeUpdate(qry);
                            qry = "UPDATE settings set val = '6' WHERE var = 'schema'";
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
                JavaCIPUnknownScope.LOG.trace(JavaCIPUnknownScope.SQL_ERROR, e2);
                JavaCIPUnknownScope.LOG.error(e2);
            }
            JavaCIPUnknownScope.LOG.trace(JavaCIPUnknownScope.SQL_ERROR, e);
            JavaCIPUnknownScope.LOG.fatal(e);
            throw new IORuntimeException("Error upgrading data store", e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                JavaCIPUnknownScope.conn.setAutoCommit(true);
            } catch (SQLRuntimeException e) {
                JavaCIPUnknownScope.LOG.trace(JavaCIPUnknownScope.SQL_ERROR, e);
                throw new IORuntimeException("Unable to cleanup SQL resources", e);
            }
        }
    }
}
