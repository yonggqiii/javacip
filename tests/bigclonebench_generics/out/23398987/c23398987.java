class c23398987 {

    public void updateItems(List<InputQueueItem> toUpdate) throws DatabaseException {
        if (toUpdate == null)
            throw new NullPointerException("toUpdate");
        try {
            JavaCIPUnknownScope.getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            JavaCIPUnknownScope.LOGGER.warn("Unable to set autocommit off", e);
        }
        try {
            PreparedStatement deleteSt = JavaCIPUnknownScope.getConnection().prepareStatement(JavaCIPUnknownScope.DELETE_ALL_ITEMS_STATEMENT);
            PreparedStatement selectCount = JavaCIPUnknownScope.getConnection().prepareStatement(JavaCIPUnknownScope.SELECT_NUMBER_ITEMS_STATEMENT);
            ResultSet rs = selectCount.executeQuery();
            rs.next();
            int totalBefore = rs.getInt(1);
            int deleted = deleteSt.executeUpdate();
            int updated = 0;
            for (InputQueueItem item : toUpdate) {
                updated += JavaCIPUnknownScope.getItemInsertStatement(item).executeUpdate();
            }
            if (totalBefore == deleted && updated == toUpdate.size()) {
                JavaCIPUnknownScope.getConnection().commit();
                JavaCIPUnknownScope.LOGGER.debug("DB has been updated. Queries: \"" + selectCount + "\" and \"" + deleteSt + "\".");
            } else {
                JavaCIPUnknownScope.getConnection().rollback();
                JavaCIPUnknownScope.LOGGER.error("DB has not been updated -> rollback! Queries: \"" + selectCount + "\" and \"" + deleteSt + "\".");
            }
        } catch (SQLException e) {
            JavaCIPUnknownScope.LOGGER.error(e);
        } finally {
            JavaCIPUnknownScope.closeConnection();
        }
    }
}
