class c14212319 {

    public String resolveItem(String oldJpgFsPath) throws DatabaseRuntimeException {
        if (oldJpgFsPath == null || "".equals(oldJpgFsPath))
            throw new NullPointerRuntimeException("oldJpgFsPath");
        try {
            JavaCIPUnknownScope.getConnection().setAutoCommit(false);
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.LOGGER.warn("Unable to set autocommit off", e);
        }
        PreparedStatement statement = null;
        String ret = null;
        try {
            statement = JavaCIPUnknownScope.getConnection().prepareStatement(JavaCIPUnknownScope.SELECT_ITEM_STATEMENT);
            statement.setString(1, oldJpgFsPath);
            ResultSet rs = statement.executeQuery();
            int i = 0;
            int id = -1;
            int rowsAffected = 0;
            while (rs.next()) {
                id = rs.getInt("id");
                ret = rs.getString("imageFile");
                i++;
            }
            if (id != -1 && new File(ret).exists()) {
                statement = JavaCIPUnknownScope.getConnection().prepareStatement(JavaCIPUnknownScope.UPDATE_ITEM_STATEMENT);
                statement.setInt(1, id);
                rowsAffected = statement.executeUpdate();
            } else {
                return null;
            }
            if (rowsAffected == 1) {
                JavaCIPUnknownScope.getConnection().commit();
                JavaCIPUnknownScope.LOGGER.debug("DB has been updated.");
            } else {
                JavaCIPUnknownScope.getConnection().rollback();
                JavaCIPUnknownScope.LOGGER.error("DB has not been updated -> rollback!");
            }
        } catch (SQLRuntimeException e) {
            JavaCIPUnknownScope.LOGGER.error(e);
        } finally {
            JavaCIPUnknownScope.closeConnection();
        }
        return ret;
    }
}
