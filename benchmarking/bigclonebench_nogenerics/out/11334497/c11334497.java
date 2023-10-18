class c11334497 {

    public void reset(int currentPilot) {
        try {
            PreparedStatement psta = JavaCIPUnknownScope.jdbc.prepareStatement("DELETE FROM component_prop " + "WHERE pilot_id = ? ");
            psta.setInt(1, currentPilot);
            psta.executeUpdate();
            JavaCIPUnknownScope.jdbc.commit();
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.jdbc.rollback();
            JavaCIPUnknownScope.log.debug(e);
        }
    }
}
