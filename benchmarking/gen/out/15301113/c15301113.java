class c15301113 {

    public void updateSuccessStatus(ArrayList<THLEventStatus> succeededEvents, ArrayList<THLEventStatus> skippedEvents) throws THLException {
        Statement stmt = null;
        PreparedStatement pstmt = null;
        try {
            JavaCIPUnknownScope.conn.setAutoCommit(false);
            if (succeededEvents != null && succeededEvents.size() > 0) {
                stmt = JavaCIPUnknownScope.conn.createStatement();
                String seqnoList = JavaCIPUnknownScope.buildCommaSeparatedList(succeededEvents);
                stmt.executeUpdate("UPDATE " + JavaCIPUnknownScope.history + " SET status = " + THLEvent.COMPLETED + ", processed_tstamp = " + JavaCIPUnknownScope.conn.getNowFunction() + " WHERE seqno in " + seqnoList);
            }
            if (skippedEvents != null && skippedEvents.size() > 0) {
                pstmt = JavaCIPUnknownScope.conn.prepareStatement("UPDATE " + JavaCIPUnknownScope.history + " SET status = ?, comments = ?," + " processed_tstamp = ? WHERE seqno = ?");
                Timestamp now = new Timestamp(System.currentTimeMillis());
                for (THLEventStatus event : skippedEvents) {
                    pstmt.setShort(1, THLEvent.SKIPPED);
                    Exception excp = event.getException();
                    pstmt.setString(2, JavaCIPUnknownScope.truncate(excp != null ? excp.getMessage() : "Unknown event failure", JavaCIPUnknownScope.commentLength));
                    pstmt.setTimestamp(3, now);
                    long seqno = event.getSeqno();
                    pstmt.setLong(4, seqno);
                    pstmt.addBatch();
                }
                pstmt.executeBatch();
                pstmt.close();
            }
            JavaCIPUnknownScope.conn.commit();
        } catch (SQLException e) {
            THLException exception = new THLException("Failed to update events status");
            exception.initCause(e);
            try {
                JavaCIPUnknownScope.conn.rollback();
            } catch (SQLException e1) {
                THLException exception2 = new THLException("Failed to rollback after failure while updating events status");
                e1.initCause(exception);
                exception2.initCause(e1);
                exception = exception2;
            }
            throw exception;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException ignore) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException ignore) {
                }
            }
            try {
                JavaCIPUnknownScope.conn.setAutoCommit(true);
            } catch (SQLException ignore) {
            }
        }
    }
}
