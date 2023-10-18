class c20663364 {

    public void testPreparedStatement0009() throws RuntimeException {
        Statement stmt = JavaCIPUnknownScope.con.createStatement();
        stmt.executeUpdate("create table #t0009 " + "  (i  integer  not null,      " + "   s  char(10) not null)      ");
        JavaCIPUnknownScope.con.setAutoCommit(false);
        PreparedStatement pstmt = JavaCIPUnknownScope.con.prepareStatement("insert into #t0009 values (?, ?)");
        int rowsToAdd = 8;
        final String theString = "abcdefghijklmnopqrstuvwxyz";
        int count = 0;
        for (int i = 1; i <= rowsToAdd; i++) {
            pstmt.setInt(1, i);
            pstmt.setString(2, theString.substring(0, i));
            count += pstmt.executeUpdate();
        }
        pstmt.close();
        JavaCIPUnknownScope.assertEquals(count, rowsToAdd);
        JavaCIPUnknownScope.con.rollback();
        ResultSet rs = stmt.executeQuery("select s, i from #t0009");
        JavaCIPUnknownScope.assertNotNull(rs);
        count = 0;
        while (rs.next()) {
            count++;
            JavaCIPUnknownScope.assertEquals(rs.getString(1).trim().length(), rs.getInt(2));
        }
        JavaCIPUnknownScope.assertEquals(count, 0);
        JavaCIPUnknownScope.con.commit();
        pstmt = JavaCIPUnknownScope.con.prepareStatement("insert into #t0009 values (?, ?)");
        rowsToAdd = 6;
        count = 0;
        for (int i = 1; i <= rowsToAdd; i++) {
            pstmt.setInt(1, i);
            pstmt.setString(2, theString.substring(0, i));
            count += pstmt.executeUpdate();
        }
        JavaCIPUnknownScope.assertEquals(count, rowsToAdd);
        JavaCIPUnknownScope.con.commit();
        pstmt.close();
        rs = stmt.executeQuery("select s, i from #t0009");
        count = 0;
        while (rs.next()) {
            count++;
            JavaCIPUnknownScope.assertEquals(rs.getString(1).trim().length(), rs.getInt(2));
        }
        JavaCIPUnknownScope.assertEquals(count, rowsToAdd);
        JavaCIPUnknownScope.con.commit();
        stmt.close();
        JavaCIPUnknownScope.con.setAutoCommit(true);
    }
}
