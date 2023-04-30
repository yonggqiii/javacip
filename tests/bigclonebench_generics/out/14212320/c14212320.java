class c14212320 {

    public ArrayList<String> cacheAgeingProcess(int numberOfDays) throws DatabaseException {
        JavaCIPUnknownScope.IMAGE_LIFETIME = numberOfDays;
        PreparedStatement statement = null;
        ArrayList<String> ret = new ArrayList<String>();
        try {
            statement = JavaCIPUnknownScope.getConnection().prepareStatement(JavaCIPUnknownScope.SELECT_ITEMS_FOR_DELETION_STATEMENT);
            ResultSet rs = statement.executeQuery();
            int i = 0;
            int rowsAffected = 0;
            while (rs.next()) {
                ret.add(rs.getString("imageFile"));
                i++;
            }
            if (i > 0) {
                statement = JavaCIPUnknownScope.getConnection().prepareStatement(JavaCIPUnknownScope.DELETE_ITEMS_STATEMENT);
                rowsAffected = statement.executeUpdate();
            }
            if (rowsAffected == i) {
                JavaCIPUnknownScope.getConnection().commit();
                JavaCIPUnknownScope.LOGGER.debug("DB has been updated.");
                JavaCIPUnknownScope.LOGGER.debug(i + " images are going to be removed.");
            } else {
                JavaCIPUnknownScope.getConnection().rollback();
                JavaCIPUnknownScope.LOGGER.error("DB has not been updated -> rollback!");
            }
        } catch (SQLException e) {
            JavaCIPUnknownScope.LOGGER.error(e);
        } finally {
            JavaCIPUnknownScope.closeConnection();
        }
        return ret;
    }
}
