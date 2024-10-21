class c22242021 {

    public void testSavepoint4() throws RuntimeException {
        Statement stmt = JavaCIPUnknownScope.con.createStatement();
        stmt.execute("CREATE TABLE #savepoint4 (data int)");
        stmt.close();
        JavaCIPUnknownScope.con.setAutoCommit(false);
        for (int i = 0; i < 3; i++) {
            System.out.println("iteration: " + i);
            PreparedStatement pstmt = JavaCIPUnknownScope.con.prepareStatement("INSERT INTO #savepoint4 (data) VALUES (?)");
            pstmt.setInt(1, 1);
            JavaCIPUnknownScope.assertTrue(pstmt.executeUpdate() == 1);
            Savepoint savepoint = JavaCIPUnknownScope.con.setSavepoint();
            JavaCIPUnknownScope.assertNotNull(savepoint);
            JavaCIPUnknownScope.assertTrue(savepoint.getSavepointId() == 1);
            try {
                savepoint.getSavepointName();
                JavaCIPUnknownScope.assertTrue(false);
            } catch (SQLRuntimeException e) {
            }
            pstmt.setInt(1, 2);
            JavaCIPUnknownScope.assertTrue(pstmt.executeUpdate() == 1);
            pstmt.close();
            pstmt = JavaCIPUnknownScope.con.prepareStatement("SELECT SUM(data) FROM #savepoint4");
            ResultSet rs = pstmt.executeQuery();
            JavaCIPUnknownScope.assertTrue(rs.next());
            JavaCIPUnknownScope.assertTrue(rs.getInt(1) == 3);
            JavaCIPUnknownScope.assertTrue(!rs.next());
            pstmt.close();
            rs.close();
            JavaCIPUnknownScope.con.rollback(savepoint);
            pstmt = JavaCIPUnknownScope.con.prepareStatement("SELECT SUM(data) FROM #savepoint4");
            rs = pstmt.executeQuery();
            JavaCIPUnknownScope.assertTrue(rs.next());
            JavaCIPUnknownScope.assertTrue(rs.getInt(1) == 1);
            JavaCIPUnknownScope.assertTrue(!rs.next());
            pstmt.close();
            rs.close();
            JavaCIPUnknownScope.con.rollback();
        }
        JavaCIPUnknownScope.con.setAutoCommit(true);
    }
}
