class c20169230 {

    public String addUserIdentity(OpenIDItem identity, long userId) throws DatabaseRuntimeException {
        if (identity == null)
            throw new NullPointerRuntimeException("identity");
        if (identity.getIdentity() == null || "".equals(identity.getIdentity()))
            throw new NullPointerRuntimeException("identity.getIdentity()");
        try {
            JavaCIPUnknownScope.getConnection().setAutoCommit(false);
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.LOGGER.warn("Unable to set autocommit off", e);
        }
        String retID = "exist";
        PreparedStatement insSt = null, seqSt = null;
        try {
            int modified = 0;
            insSt = JavaCIPUnknownScope.getConnection().prepareStatement(JavaCIPUnknownScope.INSERT_IDENTITY_STATEMENT);
            insSt.setLong(1, userId);
            insSt.setString(2, identity.getIdentity());
            modified = insSt.executeUpdate();
            seqSt = JavaCIPUnknownScope.getConnection().prepareStatement(JavaCIPUnknownScope.USER_IDENTITY_VALUE);
            ResultSet rs = seqSt.executeQuery();
            while (rs.next()) {
                retID = rs.getString(1);
            }
            if (modified == 1) {
                JavaCIPUnknownScope.getConnection().commit();
                JavaCIPUnknownScope.LOGGER.debug("DB has been updated. Queries: \"" + seqSt + "\" and \"" + insSt + "\"");
            } else {
                JavaCIPUnknownScope.getConnection().rollback();
                JavaCIPUnknownScope.LOGGER.debug("DB has not been updated -> rollback! Queries: \"" + seqSt + "\" and \"" + insSt + "\"");
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
