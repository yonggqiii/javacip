class c7286004 {

    public void store(Component component, String componentName, int currentPilot) {
        try {
            PreparedStatement psta = JavaCIPUnknownScope.jdbc.prepareStatement("UPDATE component_prop " + "SET size_height = ?, size_width = ?, pos_x = ?, pos_y = ? " + "WHERE pilot_id = ? " + "AND component_name = ?");
            psta.setInt(1, component.getHeight());
            psta.setInt(2, component.getWidth());
            Point point = component.getLocation();
            psta.setInt(3, point.x);
            psta.setInt(4, point.y);
            psta.setInt(5, currentPilot);
            psta.setString(6, componentName);
            int update = psta.executeUpdate();
            if (update == 0) {
                psta = JavaCIPUnknownScope.jdbc.prepareStatement("INSERT INTO component_prop " + "(size_height, size_width, pos_x, pos_y, pilot_id, component_name) " + "VALUES (?,?,?,?,?,?)");
                psta.setInt(1, component.getHeight());
                psta.setInt(2, component.getWidth());
                psta.setInt(3, point.x);
                psta.setInt(4, point.y);
                psta.setInt(5, currentPilot);
                psta.setString(6, componentName);
                psta.executeUpdate();
            }
            JavaCIPUnknownScope.jdbc.commit();
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.jdbc.rollback();
            JavaCIPUnknownScope.log.debug(e);
        }
    }
}
