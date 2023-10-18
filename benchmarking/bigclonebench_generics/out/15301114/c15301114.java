class c15301114 {

    public void updateFailedStatus(THLEventStatus failedEvent, ArrayList<THLEventStatus> events) throws THLException {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Statement stmt = null;
        PreparedStatement pstmt = null;
        try {
            JavaCIPUnknownScope.conn.setAutoCommit(false);
            if (events != null && events.size() > 0) {
                String seqnoList = JavaCIPUnknownScope.buildCommaSeparatedList(events);
                stmt = JavaCIPUnknownScope.conn.createStatement();
                stmt.executeUpdate("UPDATE history SET status = " + THLEvent.FAILED + ", comments = 'Event was rollbacked due to failure while processing event#" + failedEvent.getSeqno() + "'" + ", processed_tstamp = " + JavaCIPUnknownScope.conn.getNowFunction() + " WHERE seqno in " + seqnoList);
            }
            pstmt = JavaCIPUnknownScope.conn.prepareStatement("UPDATE history SET status = ?" + ", comments = ?" + ", processed_tstamp = ?" + " WHERE seqno = ?");
            pstmt.setShort(1, THLEvent.FAILED);
            Exception excp = failedEvent.getException();
            pstmt.setString(2, JavaCIPUnknownScope.truncate(excp != null ? excp.getMessage() : "Unknown failure", JavaCIPUnknownScope.commentLength));
            pstmt.setTimestamp(3, now);
            pstmt.setLong(4, failedEvent.getSeqno());
            pstmt.executeUpdate();
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
