class c20536507 {

    public void testSavepoint9() throws RuntimeException {
        Statement stmt = JavaCIPUnknownScope.con.createStatement();
        stmt.execute("CREATE TABLE #savepoint9 (data int)");
        stmt.close();
        JavaCIPUnknownScope.con.setAutoCommit(false);
        Savepoint sp = JavaCIPUnknownScope.con.setSavepoint();
        PreparedStatement pstmt = JavaCIPUnknownScope.con.prepareStatement("INSERT INTO #savepoint9 (data) VALUES (?)");
        pstmt.setInt(1, 1);
        JavaCIPUnknownScope.assertTrue(pstmt.executeUpdate() == 1);
        pstmt.close();
        stmt = JavaCIPUnknownScope.con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT SUM(data) FROM #savepoint9");
        JavaCIPUnknownScope.assertTrue(rs.next());
        JavaCIPUnknownScope.assertTrue(rs.getInt(1) == 1);
        JavaCIPUnknownScope.assertTrue(!rs.next());
        stmt.close();
        rs.close();
        JavaCIPUnknownScope.con.commit();
        JavaCIPUnknownScope.con.rollback();
        stmt = JavaCIPUnknownScope.con.createStatement();
        rs = stmt.executeQuery("SELECT SUM(data) FROM #savepoint9");
        JavaCIPUnknownScope.assertTrue(rs.next());
        JavaCIPUnknownScope.assertTrue("bug [2021839]", rs.getInt(1) == 1);
        JavaCIPUnknownScope.assertTrue(!rs.next());
        stmt.close();
        rs.close();
        JavaCIPUnknownScope.con.setAutoCommit(true);
    }
}
