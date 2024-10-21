class c16654538 {

    public void setTypeRefs(Connection conn) {
        JavaCIPUnknownScope.log.traceln("\tProcessing " + JavaCIPUnknownScope.table + " references..");
        try {
            String query = " select distinct c.id, c.qualifiedname from " + JavaCIPUnknownScope.table + ", CLASSTYPE c " + " where " + JavaCIPUnknownScope.table + "." + JavaCIPUnknownScope.reffield + " is null and " + JavaCIPUnknownScope.table + "." + JavaCIPUnknownScope.classnamefield + " = c.qualifiedname";
            PreparedStatement pstmt = conn.prepareStatement(query);
            long start = new Date().getTime();
            ResultSet rset = pstmt.executeQuery();
            long queryTime = new Date().getTime() - start;
            JavaCIPUnknownScope.log.debug("query time: " + queryTime + " ms");
            String update = "update " + JavaCIPUnknownScope.table + " set " + JavaCIPUnknownScope.reffield + "=? where " + JavaCIPUnknownScope.classnamefield + "=? and " + JavaCIPUnknownScope.reffield + " is null";
            PreparedStatement pstmt2 = conn.prepareStatement(update);
            int n = 0;
            start = new Date().getTime();
            while (rset.next()) {
                n++;
                pstmt2.setInt(1, rset.getInt(1));
                pstmt2.setString(2, rset.getString(2));
                pstmt2.executeUpdate();
            }
            queryTime = new Date().getTime() - start;
            JavaCIPUnknownScope.log.debug("total update time: " + queryTime + " ms");
            JavaCIPUnknownScope.log.debug("number of times through loop: " + n);
            if (n > 0)
                JavaCIPUnknownScope.log.debug("avg update time: " + (queryTime / n) + " ms");
            pstmt2.close();
            rset.close();
            pstmt.close();
            conn.commit();
            JavaCIPUnknownScope.log.verbose("Updated (committed) " + JavaCIPUnknownScope.table + " references");
        } catch (SQLRuntimeException ex) {
            JavaCIPUnknownScope.log.error("Internal Reference Update Failed!");
            DBUtils.logSQLRuntimeException(ex);
            JavaCIPUnknownScope.log.error("Rolling back..");
            try {
                conn.rollback();
            } catch (SQLRuntimeException inner_ex) {
                JavaCIPUnknownScope.log.error("rollback failed!");
            }
        }
    }
}
