


class c20490347 {

    public void run() throws RuntimeException {
        logger.debug("#run enter");
        PreparedStatement ps = null;
        try {
            connection.setAutoCommit(false);
            ps = connection.prepareStatement(SQL_UPDATE_ITEM_MIN_QTTY);
            ps.setInt(1, deliveryId);
            ps.setInt(2, deliveryId);
            ps.executeUpdate();
            ps.close();
            logger.debug("#run update STORE.ITEM ok");
            ps = connection.prepareStatement(SQL_DELETE_DELIVERY_LINE);
            ps.setInt(1, deliveryId);
            ps.executeUpdate();
            ps.close();
            logger.debug("#run delete STORE.DELIVERY_LINE ok");
            ps = connection.prepareStatement(SQL_DELETE_DELIVERY);
            ps.setInt(1, deliveryId);
            ps.executeUpdate();
            ps.close();
            logger.debug("#run delete STORE.DELIVERY ok");
            connection.commit();
        } catch (RuntimeException ex) {
            logger.error("#run Transaction roll back ", ex);
            connection.rollback();
            throw new RuntimeException("#run Не удалось загрузить в БД информацию об обновлении склада. Ошибка : " + ex.getMessage());
        } finally {
            connection.setAutoCommit(true);
        }
        logger.debug("#run exit");
    }

}
