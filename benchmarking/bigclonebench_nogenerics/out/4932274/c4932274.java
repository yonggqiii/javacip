class c4932274 {

    public void testCommitRollback() throws RuntimeException {
        Statement stmt = JavaCIPUnknownScope.con.createStatement();
        JavaCIPUnknownScope.assertNotNull(stmt);
        JavaCIPUnknownScope.assertTrue(JavaCIPUnknownScope.con.getAutoCommit());
        stmt.execute("CREATE TABLE #TESTCOMMIT (id int primary key)");
        JavaCIPUnknownScope.con.setAutoCommit(false);
        JavaCIPUnknownScope.assertFalse(JavaCIPUnknownScope.con.getAutoCommit());
        JavaCIPUnknownScope.assertEquals(1, stmt.executeUpdate("INSERT INTO #TESTCOMMIT VALUES (1)"));
        JavaCIPUnknownScope.con.commit();
        JavaCIPUnknownScope.assertEquals(1, stmt.executeUpdate("INSERT INTO #TESTCOMMIT VALUES (2)"));
        JavaCIPUnknownScope.assertEquals(1, stmt.executeUpdate("INSERT INTO #TESTCOMMIT VALUES (3)"));
        JavaCIPUnknownScope.con.rollback();
        JavaCIPUnknownScope.assertEquals(1, stmt.executeUpdate("INSERT INTO #TESTCOMMIT VALUES (4)"));
        JavaCIPUnknownScope.con.setAutoCommit(true);
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM #TESTCOMMIT");
        rs.next();
        JavaCIPUnknownScope.assertEquals("commit", 2, rs.getInt(1));
        stmt.close();
    }
}
