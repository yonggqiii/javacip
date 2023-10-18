


class c9096319 {

    private void handleNodeRegainedService(long eventID, long nodeID, String ipAddr, long serviceID, String eventTime) {
        Category log = ThreadCategory.getInstance(OutageWriter.class);
        if (eventID == -1 || nodeID == -1 || ipAddr == null || serviceID == -1) {
            log.warn(EventConstants.NODE_REGAINED_SERVICE_EVENT_UEI + " ignored - info incomplete - eventid/nodeid/ip/svc: " + eventID + "/" + nodeID + "/" + ipAddr + "/" + serviceID);
            return;
        }
        Connection dbConn = null;
        try {
            dbConn = DatabaseConnectionFactory.getInstance().getConnection();
            if (openOutageExists(dbConn, nodeID, ipAddr, serviceID)) {
                try {
                    dbConn.setAutoCommit(false);
                } catch (SQLRuntimeException sqle) {
                    log.error("Unable to change database AutoCommit to FALSE", sqle);
                    return;
                }
                PreparedStatement outageUpdater = dbConn.prepareStatement(OutageConstants.DB_UPDATE_OUTAGE_FOR_SERVICE);
                outageUpdater.setLong(1, eventID);
                outageUpdater.setTimestamp(2, convertEventTimeIntoTimestamp(eventTime));
                outageUpdater.setLong(3, nodeID);
                outageUpdater.setString(4, ipAddr);
                outageUpdater.setLong(5, serviceID);
                outageUpdater.executeUpdate();
                outageUpdater.close();
                try {
                    dbConn.commit();
                    if (log.isDebugEnabled()) log.debug("nodeRegainedService: closed outage for nodeid/ip/service " + nodeID + "/" + ipAddr + "/" + serviceID + " in DB");
                } catch (SQLRuntimeException se) {
                    log.warn("Rolling back transaction, nodeRegainedService could not be recorded  for nodeId/ipAddr/service: " + nodeID + "/" + ipAddr + "/" + serviceID, se);
                    try {
                        dbConn.rollback();
                    } catch (SQLRuntimeException sqle) {
                        log.warn("SQL exception during rollback, reason", sqle);
                    }
                }
            } else {
                log.warn("\'" + EventConstants.NODE_REGAINED_SERVICE_EVENT_UEI + "\' for " + nodeID + "/" + ipAddr + "/" + serviceID + " does not have open record.");
            }
        } catch (SQLRuntimeException se) {
            log.warn("SQL exception while handling \'nodeRegainedService\'", se);
        } finally {
            try {
                if (dbConn != null) dbConn.close();
            } catch (SQLRuntimeException e) {
                log.warn("RuntimeException closing JDBC connection", e);
            }
        }
    }

}
