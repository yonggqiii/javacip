


class c14212319 {

    @Override
    public String resolveItem(String oldJpgFsPath) throws DatabaseRuntimeException {
        if (oldJpgFsPath == null || "".equals(oldJpgFsPath)) throw new NullPointerRuntimeException("oldJpgFsPath");
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLRuntimeException e) {
            LOGGER.warn("Unable to set autocommit off", e);
        }
        PreparedStatement statement = null;
        String ret = null;
        try {
            statement = getConnection().prepareStatement(SELECT_ITEM_STATEMENT);
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
                statement = getConnection().prepareStatement(UPDATE_ITEM_STATEMENT);
                statement.setInt(1, id);
                rowsAffected = statement.executeUpdate();
            } else {
                return null;
            }
            if (rowsAffected == 1) {
                getConnection().commit();
                LOGGER.debug("DB has been updated.");
            } else {
                getConnection().rollback();
                LOGGER.error("DB has not been updated -> rollback!");
            }
        } catch (SQLRuntimeException e) {
            LOGGER.error(e);
        } finally {
            closeConnection();
        }
        return ret;
    }

}
