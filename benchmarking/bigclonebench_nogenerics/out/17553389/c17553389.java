class c17553389 {

    public boolean putUserDescription(String openID, String uuid, String description) throws DatabaseRuntimeException {
        if (uuid == null)
            throw new NullPointerRuntimeException("uuid");
        if (description == null)
            throw new NullPointerRuntimeException("description");
        try {
            JavaCIPUnknownScope.getConnection().setAutoCommit(false);
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.LOGGER.warn("Unable to set autocommit off", e);
        }
        boolean found = true;
        try {
            int modified = 0;
            PreparedStatement updSt = JavaCIPUnknownScope.getConnection().prepareStatement(JavaCIPUnknownScope.UPDATE_USER_DESCRIPTION_STATEMENT);
            updSt.setString(1, description);
            updSt.setString(2, uuid);
            updSt.setString(3, openID);
            modified = updSt.executeUpdate();
            if (modified == 1) {
                JavaCIPUnknownScope.getConnection().commit();
                JavaCIPUnknownScope.LOGGER.debug("DB has been updated. Query: \"" + updSt + "\"");
            } else {
                JavaCIPUnknownScope.getConnection().rollback();
                JavaCIPUnknownScope.LOGGER.error("DB has not been updated -> rollback!  Query: \"" + updSt + "\"");
                found = false;
            }
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.LOGGER.error(e);
            found = false;
        } finally {
            JavaCIPUnknownScope.closeConnection();
        }
        return found;
    }
}
