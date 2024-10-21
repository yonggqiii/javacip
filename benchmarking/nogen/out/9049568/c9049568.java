class c9049568 {

    public void reset(String componentName, int currentPilot) {
        try {
            PreparedStatement psta = JavaCIPUnknownScope.jdbc.prepareStatement("DELETE FROM component_prop " + "WHERE pilot_id = ? " + "AND component_name = ?");
            psta.setInt(1, currentPilot);
            psta.setString(2, componentName);
            psta.executeUpdate();
            JavaCIPUnknownScope.jdbc.commit();
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.jdbc.rollback();
            JavaCIPUnknownScope.log.debug(e);
        }
    }
}
