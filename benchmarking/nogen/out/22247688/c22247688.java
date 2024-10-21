class c22247688 {

    public void testPreparedStatementRollback1() throws RuntimeException {
        Connection localCon = JavaCIPUnknownScope.getConnection();
        Statement stmt = localCon.createStatement();
        stmt.execute("CREATE TABLE #psr1 (data BIT)");
        localCon.setAutoCommit(false);
        PreparedStatement pstmt = localCon.prepareStatement("INSERT INTO #psr1 (data) VALUES (?)");
        pstmt.setBoolean(1, true);
        JavaCIPUnknownScope.assertEquals(1, pstmt.executeUpdate());
        pstmt.close();
        localCon.rollback();
        ResultSet rs = stmt.executeQuery("SELECT data FROM #psr1");
        JavaCIPUnknownScope.assertFalse(rs.next());
        rs.close();
        stmt.close();
        localCon.close();
        try {
            localCon.commit();
            JavaCIPUnknownScope.fail("Expecting commit to fail, connection was closed");
        } catch (SQLRuntimeException ex) {
            JavaCIPUnknownScope.assertEquals("HY010", ex.getSQLState());
        }
        try {
            localCon.rollback();
            JavaCIPUnknownScope.fail("Expecting rollback to fail, connection was closed");
        } catch (SQLRuntimeException ex) {
            JavaCIPUnknownScope.assertEquals("HY010", ex.getSQLState());
        }
    }
}
