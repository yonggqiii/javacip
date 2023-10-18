class c13222049 {

    public void testAutoCommit() throws RuntimeException {
        Connection con = JavaCIPUnknownScope.getConnectionOverrideProperties(new Properties());
        try {
            Statement stmt = con.createStatement();
            JavaCIPUnknownScope.assertEquals(0, stmt.executeUpdate("create table #testAutoCommit (i int)"));
            con.setAutoCommit(false);
            JavaCIPUnknownScope.assertEquals(1, stmt.executeUpdate("insert into #testAutoCommit (i) values (0)"));
            con.setAutoCommit(false);
            con.rollback();
            JavaCIPUnknownScope.assertEquals(1, stmt.executeUpdate("insert into #testAutoCommit (i) values (1)"));
            con.setAutoCommit(true);
            con.setAutoCommit(false);
            con.rollback();
            con.setAutoCommit(true);
            ResultSet rs = stmt.executeQuery("select i from #testAutoCommit");
            JavaCIPUnknownScope.assertTrue(rs.next());
            JavaCIPUnknownScope.assertEquals(1, rs.getInt(1));
            JavaCIPUnknownScope.assertFalse(rs.next());
            rs.close();
            stmt.close();
        } finally {
            con.close();
        }
    }
}
