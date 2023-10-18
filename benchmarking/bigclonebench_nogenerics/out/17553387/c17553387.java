class c17553387 {

    public boolean putDescription(String uuid, String description) throws DatabaseRuntimeException {
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
            PreparedStatement findSt = JavaCIPUnknownScope.getConnection().prepareStatement(JavaCIPUnknownScope.SELECT_COMMON_DESCRIPTION_STATEMENT);
            PreparedStatement updSt = null;
            findSt.setString(1, uuid);
            ResultSet rs = findSt.executeQuery();
            found = rs.next();
            int modified = 0;
            updSt = JavaCIPUnknownScope.getConnection().prepareStatement(found ? JavaCIPUnknownScope.UPDATE_COMMON_DESCRIPTION_STATEMENT : JavaCIPUnknownScope.INSERT_COMMON_DESCRIPTION_STATEMENT);
            updSt.setString(1, description);
            updSt.setString(2, uuid);
            modified = updSt.executeUpdate();
            if (modified == 1) {
                JavaCIPUnknownScope.getConnection().commit();
                JavaCIPUnknownScope.LOGGER.debug("DB has been updated. Queries: \"" + findSt + "\" and \"" + updSt + "\"");
            } else {
                JavaCIPUnknownScope.getConnection().rollback();
                JavaCIPUnknownScope.LOGGER.error("DB has not been updated -> rollback! Queries: \"" + findSt + "\" and \"" + updSt + "\"");
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
