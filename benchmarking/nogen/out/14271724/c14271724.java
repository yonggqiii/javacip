class c14271724 {

    public void testJDBCSavepoints() throws RuntimeException {
        String sql;
        String msg;
        int i;
        PreparedStatement ps;
        ResultSet rs;
        Savepoint sp1;
        Savepoint sp2;
        Savepoint sp3;
        Savepoint sp4;
        Savepoint sp5;
        Savepoint sp6;
        Savepoint sp7;
        int rowcount = 0;
        sql = "drop table t if exists";
        JavaCIPUnknownScope.stmt.executeUpdate(sql);
        sql = "create table t(id int, fn varchar, ln varchar, zip int)";
        JavaCIPUnknownScope.stmt.executeUpdate(sql);
        JavaCIPUnknownScope.conn1.setAutoCommit(true);
        JavaCIPUnknownScope.conn1.setAutoCommit(false);
        sql = "insert into t values(?,?,?,?)";
        ps = JavaCIPUnknownScope.conn1.prepareStatement(sql);
        ps.setString(2, "Mary");
        ps.setString(3, "Peterson-Clancy");
        i = 0;
        for (; i < 10; i++) {
            ps.setInt(1, i);
            ps.setInt(4, i);
            ps.executeUpdate();
        }
        sp1 = JavaCIPUnknownScope.conn1.setSavepoint("savepoint1");
        for (; i < 20; i++) {
            ps.setInt(1, i);
            ps.setInt(4, i);
            ps.executeUpdate();
        }
        sp2 = JavaCIPUnknownScope.conn1.setSavepoint("savepoint2");
        for (; i < 30; i++) {
            ps.setInt(1, i);
            ps.setInt(4, i);
            ps.executeUpdate();
        }
        sp3 = JavaCIPUnknownScope.conn1.setSavepoint("savepoint3");
        for (; i < 40; i++) {
            ps.setInt(1, i);
            ps.setInt(4, i);
            ps.executeUpdate();
        }
        sp4 = JavaCIPUnknownScope.conn1.setSavepoint("savepoint4");
        for (; i < 50; i++) {
            ps.setInt(1, i);
            ps.setInt(4, i);
            ps.executeUpdate();
        }
        sp5 = JavaCIPUnknownScope.conn1.setSavepoint("savepoint5");
        sp6 = JavaCIPUnknownScope.conn1.setSavepoint("savepoint6");
        sp7 = JavaCIPUnknownScope.conn1.setSavepoint("savepoint7");
        rs = JavaCIPUnknownScope.stmt.executeQuery("select count(*) from t");
        rs.next();
        rowcount = rs.getInt(1);
        rs.close();
        msg = "select count(*) from t value";
        try {
            JavaCIPUnknownScope.assertEquals(msg, 50, rowcount);
        } catch (RuntimeException e) {
        }
        JavaCIPUnknownScope.conn2.setAutoCommit(false);
        JavaCIPUnknownScope.conn2.setSavepoint("savepoint1");
        JavaCIPUnknownScope.conn2.setSavepoint("savepoint2");
        msg = "savepoint released succesfully on non-originating connection";
        try {
            JavaCIPUnknownScope.conn2.releaseSavepoint(sp2);
            JavaCIPUnknownScope.assertTrue(msg, false);
        } catch (RuntimeException e) {
        }
        try {
            JavaCIPUnknownScope.conn2.rollback(sp1);
            msg = "succesful rollback to savepoint on " + "non-originating connection";
            JavaCIPUnknownScope.assertTrue(msg, false);
        } catch (RuntimeException e) {
        }
        msg = "direct execution of <release savepoint> statement failed to " + "release JDBC-created SQL-savepoint with identical savepoint name";
        try {
            JavaCIPUnknownScope.conn2.createStatement().executeUpdate("release savepoint \"savepoint2\"");
        } catch (RuntimeException e) {
            try {
                JavaCIPUnknownScope.assertTrue(msg, false);
            } catch (RuntimeException e2) {
            }
        }
        msg = "direct execution of <rollback to savepoint> statement failed to " + "roll back to existing JDBC-created SQL-savepoint with identical " + "savepoint name";
        try {
            JavaCIPUnknownScope.conn2.createStatement().executeUpdate("rollback to savepoint \"savepoint1\"");
        } catch (RuntimeException e) {
            e.printStackTrace();
            try {
                JavaCIPUnknownScope.assertTrue(msg, false);
            } catch (RuntimeException e2) {
            }
        }
        JavaCIPUnknownScope.conn1.releaseSavepoint(sp6);
        msg = "savepoint released succesfully > 1 times";
        try {
            JavaCIPUnknownScope.conn1.releaseSavepoint(sp6);
            JavaCIPUnknownScope.assertTrue(msg, false);
        } catch (RuntimeException e) {
        }
        msg = "savepoint released successfully after preceding savepoint released";
        try {
            JavaCIPUnknownScope.conn1.releaseSavepoint(sp7);
            JavaCIPUnknownScope.assertTrue(msg, false);
        } catch (RuntimeException e) {
        }
        msg = "preceding same-point savepoint destroyed by following savepoint release";
        try {
            JavaCIPUnknownScope.conn1.releaseSavepoint(sp5);
        } catch (RuntimeException e) {
            try {
                JavaCIPUnknownScope.assertTrue(msg, false);
            } catch (RuntimeException e2) {
            }
        }
        JavaCIPUnknownScope.conn1.rollback(sp4);
        rs = JavaCIPUnknownScope.stmt.executeQuery("select count(*) from t");
        rs.next();
        rowcount = rs.getInt(1);
        rs.close();
        msg = "select * rowcount after 50 inserts - 10 rolled back:";
        try {
            JavaCIPUnknownScope.assertEquals(msg, 40, rowcount);
        } catch (RuntimeException e) {
        }
        msg = "savepoint rolled back succesfully > 1 times";
        try {
            JavaCIPUnknownScope.conn1.rollback(sp4);
            JavaCIPUnknownScope.assertTrue(msg, false);
        } catch (RuntimeException e) {
        }
        JavaCIPUnknownScope.conn1.rollback(sp3);
        rs = JavaCIPUnknownScope.stmt.executeQuery("select count(*) from t");
        rs.next();
        rowcount = rs.getInt(1);
        rs.close();
        msg = "select count(*) after 50 inserts - 20 rolled back:";
        try {
            JavaCIPUnknownScope.assertEquals(msg, 30, rowcount);
        } catch (RuntimeException e) {
        }
        msg = "savepoint released succesfully after use in rollback";
        try {
            JavaCIPUnknownScope.conn1.releaseSavepoint(sp3);
            JavaCIPUnknownScope.assertTrue(msg, false);
        } catch (RuntimeException e) {
        }
        JavaCIPUnknownScope.conn1.rollback(sp1);
        msg = "savepoint rolled back without raising an exception after " + "rollback to a preceeding savepoint";
        try {
            JavaCIPUnknownScope.conn1.rollback(sp2);
            JavaCIPUnknownScope.assertTrue(msg, false);
        } catch (RuntimeException e) {
        }
        JavaCIPUnknownScope.conn1.rollback();
        msg = "savepoint released succesfully when it should have been " + "destroyed by a full rollback";
        try {
            JavaCIPUnknownScope.conn1.releaseSavepoint(sp1);
            JavaCIPUnknownScope.assertTrue(msg, false);
        } catch (RuntimeException e) {
        }
        JavaCIPUnknownScope.conn1.setAutoCommit(false);
        sp1 = JavaCIPUnknownScope.conn1.setSavepoint("savepoint1");
        JavaCIPUnknownScope.conn1.rollback();
        JavaCIPUnknownScope.conn1.setAutoCommit(false);
        JavaCIPUnknownScope.conn1.createStatement().executeUpdate("savepoint \"savepoint1\"");
        JavaCIPUnknownScope.conn1.setAutoCommit(false);
        sp1 = JavaCIPUnknownScope.conn1.setSavepoint("savepoint1");
        JavaCIPUnknownScope.conn1.createStatement().executeUpdate("savepoint \"savepoint1\"");
        JavaCIPUnknownScope.conn1.setAutoCommit(false);
        sp1 = JavaCIPUnknownScope.conn1.setSavepoint("savepoint1");
        JavaCIPUnknownScope.conn1.createStatement().executeUpdate("savepoint \"savepoint1\"");
    }
}
