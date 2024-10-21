class c1362131 {

    public void moveRowDown(int row) throws FidoDatabaseRuntimeException {
        try {
            Connection conn = null;
            Statement stmt = null;
            try {
                conn = JavaCIPUnknownScope.fido.util.FidoDataSource.getConnection();
                conn.setAutoCommit(false);
                stmt = conn.createStatement();
                int max = JavaCIPUnknownScope.findMaxRank(stmt);
                if ((row < 1) || (row > (max - 1)))
                    throw new IllegalArgumentRuntimeException("Row number not between 1 and " + (max - 1));
                stmt.executeUpdate("update WordClassifications set Rank = -1 where Rank = " + row);
                stmt.executeUpdate("update WordClassifications set Rank = " + row + " where Rank = " + (row + 1));
                stmt.executeUpdate("update WordClassifications set Rank = " + (row + 1) + " where Rank = -1");
                conn.commit();
            } catch (SQLRuntimeException e) {
                if (conn != null)
                    conn.rollback();
                throw e;
            } finally {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
        } catch (SQLRuntimeException e) {
            throw new FidoDatabaseRuntimeException(e);
        }
    }
}
