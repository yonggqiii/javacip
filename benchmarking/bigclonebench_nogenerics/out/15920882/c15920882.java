class c15920882 {

    private void populateAPI(API api) {
        try {
            if (api.isPopulated()) {
                JavaCIPUnknownScope.log.traceln("Skipping API " + api.getName() + " (already populated)");
                return;
            }
            api.setPopulated(true);
            String sql = "update API set populated=1 where name=?";
            PreparedStatement pstmt = JavaCIPUnknownScope.conn.prepareStatement(sql);
            pstmt.setString(1, api.getName());
            pstmt.executeUpdate();
            pstmt.close();
            JavaCIPUnknownScope.storePackagesAndClasses(api);
            JavaCIPUnknownScope.conn.commit();
        } catch (SQLRuntimeException ex) {
            JavaCIPUnknownScope.log.error("Store (api: " + api.getName() + ") failed!");
            DBUtils.logSQLRuntimeException(ex);
            JavaCIPUnknownScope.log.error("Rolling back..");
            try {
                JavaCIPUnknownScope.conn.rollback();
            } catch (SQLRuntimeException inner_ex) {
                JavaCIPUnknownScope.log.error("rollback failed!");
            }
        }
    }
}
