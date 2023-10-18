class c20169227 {

    public String addUser(UserInfoItem user) throws DatabaseRuntimeException {
        if (user == null)
            throw new NullPointerRuntimeException("user");
        if (user.getSurname() == null || "".equals(user.getSurname()))
            throw new NullPointerRuntimeException("user.getSurname()");
        try {
            JavaCIPUnknownScope.getConnection().setAutoCommit(false);
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.LOGGER.warn("Unable to set autocommit off", e);
        }
        String retID = "exist";
        PreparedStatement insSt = null, updSt = null, seqSt = null;
        try {
            int modified = 0;
            if (user.getId() != null) {
                long id = Long.parseLong(user.getId());
                updSt = JavaCIPUnknownScope.getConnection().prepareStatement(JavaCIPUnknownScope.UPDATE_USER_STATEMENT);
                updSt.setString(1, user.getName());
                updSt.setString(2, user.getSurname());
                updSt.setLong(3, id);
                modified = updSt.executeUpdate();
            } else {
                insSt = JavaCIPUnknownScope.getConnection().prepareStatement(JavaCIPUnknownScope.INSERT_USER_STATEMENT);
                insSt.setString(1, user.getName());
                insSt.setString(2, user.getSurname());
                insSt.setBoolean(3, "m".equalsIgnoreCase(user.getSex()));
                modified = insSt.executeUpdate();
                seqSt = JavaCIPUnknownScope.getConnection().prepareStatement(JavaCIPUnknownScope.USER_CURR_VALUE);
                ResultSet rs = seqSt.executeQuery();
                while (rs.next()) {
                    retID = rs.getString(1);
                }
            }
            if (modified == 1) {
                JavaCIPUnknownScope.getConnection().commit();
                JavaCIPUnknownScope.LOGGER.debug("DB has been updated. Queries: \"" + seqSt + "\" and \"" + (user.getId() != null ? updSt : insSt) + "\"");
            } else {
                JavaCIPUnknownScope.getConnection().rollback();
                JavaCIPUnknownScope.LOGGER.debug("DB has not been updated. -> rollback! Queries: \"" + seqSt + "\" and \"" + (user.getId() != null ? updSt : insSt) + "\"");
                retID = "error";
            }
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.LOGGER.error(e);
            retID = "error";
        } finally {
            JavaCIPUnknownScope.closeConnection();
        }
        return retID;
    }
}
