


class c4593012 {

    private void upgradeSchema() throws IORuntimeException {
        Statement stmt = null;
        try {
            int i = getSchema();
            LOG.info("DB is currently at schema " + i);
            if (i < SCHEMA_VERSION) {
                LOG.info("Upgrading from schema " + i + " to schema " + SCHEMA_VERSION);
                conn.setAutoCommit(false);
                stmt = conn.createStatement();
                while (i < SCHEMA_VERSION) {
                    String qry;
                    switch(i) {
                        case 1:
                            qry = "UPDATE settings SET val = '2' WHERE var = 'schema'";
                            stmt.executeUpdate(qry);
                            break;
                    }
                    i++;
                }
                conn.commit();
            }
        } catch (SQLRuntimeException e) {
            try {
                conn.rollback();
            } catch (SQLRuntimeException e2) {
                LOG.error(SQL_ERROR, e2);
            }
            LOG.fatal(SQL_ERROR, e);
            throw new IORuntimeException("Error upgrading data store", e);
        } finally {
            try {
                if (stmt != null) stmt.close();
                conn.setAutoCommit(true);
            } catch (SQLRuntimeException e) {
                LOG.error(SQL_ERROR, e);
                throw new IORuntimeException("Unable to cleanup SQL resources", e);
            }
        }
    }

}
